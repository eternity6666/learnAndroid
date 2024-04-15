package com.yzh.demoapp.program

import android.os.Build
import androidx.annotation.RequiresApi
import com.yzh.demoapp.base.network.okHttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.time.LocalDateTime
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class XSDownloader {
    private val client: OkHttpClient
        get() = okHttpClient
    private val waitOpenUrlList = mutableSetOf<String>()
    private val successOpenUrlList = mutableSetOf<String>()
    private val failureOpenUrlList = mutableSetOf<String>()
    private val downloadUrlList = mutableSetOf<String>()
    private val downloadUrlCachedList = mutableSetOf<String>()
    private val urlSet = mutableSetOf<String>()
    private var writeJob: Job? = null

    fun sortFiles() = runBlocking {
        val startTime = System.currentTimeMillis()
        println("${currentTime()} 开始排序")
        runCatching {
            val keyList = listOf(
                KEY_SUCCESS_OPEN_FILE_NAME,
                KEY_FAILURE_OPEN_FILE_NAME,
                KEY_DOWNLOAD_URL_FILE_NAME
            )
            keyList.map { fileName ->
                async {
                    val lines = readFromFile(fileName).sorted()
                    writeToFile(fileName, lines)
                }
            }.awaitAll()
        }
        println("${currentTime()} 排序结束 耗时=${System.currentTimeMillis() - startTime}")
    }

    fun download(host: String, url: String) = runBlocking {
        val startTime = System.currentTimeMillis()
        println("${currentTime()} 开始下载")
        runCatching {
            successOpenUrlList.clear()
            successOpenUrlList.addAll(readFromFile(KEY_SUCCESS_OPEN_FILE_NAME))
            failureOpenUrlList.clear()

            downloadUrlList.clear()
            downloadUrlCachedList.addAll(readFromFile(KEY_DOWNLOAD_URL_FILE_NAME))

            urlSet.clear()
            urlSet.addAll(successOpenUrlList)
            urlSet.addAll(downloadUrlCachedList)

            waitOpenUrlList.clear()
            waitOpenUrlList.add(url)
            waitOpenUrlList.addAll(readFromFile(KEY_FAILURE_OPEN_FILE_NAME))

            initWriteJob()
            initOpenUrlJob(this, host)
            while (downloadUrlList.isNotEmpty()) {
                delay(100)
            }
            writeJob?.cancel()
            writeToFile(KEY_FAILURE_OPEN_FILE_NAME, failureOpenUrlList.toList())
            writeToFile(KEY_SUCCESS_OPEN_FILE_NAME, successOpenUrlList.toList())
        }.onFailure {
            it.printStackTrace()
        }
        println("${currentTime()} 下载结束 耗时=${System.currentTimeMillis() - startTime}")
    }

    private suspend fun initOpenUrlJob(scope: CoroutineScope, host: String) {
        val asyncList = mutableListOf<Deferred<*>>()
        while (waitOpenUrlList.isNotEmpty()) {
            val tmpNeedOpenUrl = waitOpenUrlList.take(COUNT_OPEN_URL)
            tmpNeedOpenUrl.forEach { waitOpenUrlList.remove(it) }
            val asyncItem = scope.async {
                val newOpenUrlList = realOpenUrl(
                    host = host,
                    tmpNeedOpenUrl.filterNot { successOpenUrlList.contains(it) }
                ).filterNot { urlSet.contains(it) }
                println("${currentTime()} realOpenUrl, newOpenUrlListSize=${newOpenUrlList.size}")
                urlSet.addAll(newOpenUrlList)
                waitOpenUrlList.addAll(newOpenUrlList)
            }
            if (waitOpenUrlList.isEmpty()) {
                asyncItem.await()
            } else {
                asyncList.add(asyncItem)
                if (asyncList.size >= 30) {
                    asyncList.awaitAll()
                    asyncList.clear()
                }
            }
        }
        asyncList.awaitAll()
        asyncList.clear()
        println("${currentTime()} initOpenUrlJob finish")
    }

    private suspend fun realOpenUrl(host: String, needOpenUrlList: List<String>): Set<String> {
        val tmpList = needOpenUrlList.toMutableList()
        val resultList = mutableSetOf<String>()
        while (tmpList.isNotEmpty()) {
            val url = tmpList.removeAt(0)
            if (successOpenUrlList.contains(url)) {
                continue
            }
            println("${currentTime()} try open ${host + url} queue size=${this.waitOpenUrlList.size}")
            runCatching {
                readHtml(host + url)
            }.onFailure {
                it.printStackTrace()
                failureOpenUrlList.add(url)
            }.onSuccess { html ->
                successOpenUrlList.add(url)
                resultList.addAll(fetchLinkList(html, tagName = "a", key = "href"))
                resultList.addAll(fetchLinkList(html, tagName = "option", key = "value"))
            }
        }
        return resultList
    }

    private fun initWriteJob() {
        writeJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val realNeedWriteUrl = downloadUrlList.take(COUNT_PAGE_DOWNLOAD_URL)
                if (realNeedWriteUrl.isEmpty()) {
                    delay(1000)
                    continue
                }
                realNeedWriteUrl.forEach { downloadUrlList.remove(it) }
                val result = appendToFile(KEY_DOWNLOAD_URL_FILE_NAME, realNeedWriteUrl)
                println("${currentTime()} 写入${realNeedWriteUrl.size}个链接 result=$result")
            }
        }
    }

    private fun fetchLinkList(html: String, tagName: String, key: String): Set<String> {
        val elements = html.getElementsByTag(tagName)
        val linkList = mutableSetOf<String>()
        for (element in elements) {
            val value = element.attribute(key)?.value.orEmpty()
            if (value.endsWith(".html")) {
                if (value.startsWith("/du") || value.startsWith("/Shtml")) {
                    continue
                }
            }
            if (value.startsWith("/du")) {
                if (downloadUrlCachedList.contains(value)) {
                    continue
                }
                downloadUrlCachedList.add(value)
                downloadUrlList.add(value)
            } else if (value.startsWith("/") && !successOpenUrlList.contains(value)) {
                linkList.add(value)
            }
        }
        return linkList
    }

    private suspend fun readHtml(url: String): String {
        val request = Request.Builder()
            .url(url)
            .addHeaders(headers)
            .build()
        return suspendCoroutine {
            client.newCall(request = request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    it.resumeWith(Result.success(response.body?.string().orEmpty()))
                }
            })
        }
    }

    companion object {
        private const val KEY_SUCCESS_OPEN_FILE_NAME = "xsdownloder_success_open.txt"
        private const val KEY_FAILURE_OPEN_FILE_NAME = "xsdownloder_failure_open.txt"
        private const val KEY_DOWNLOAD_URL_FILE_NAME = "xsdownloder_download_url.txt"
        private const val COUNT_PAGE_DOWNLOAD_URL = 200
        private const val COUNT_OPEN_URL = 20
        private val headers = mapOf(
            "Accept-Language" to "zh-CN,zh;q=0.9",
            "User-Agent" to "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"
        )

        private fun readFromFile(fileName: String): Set<String> {
            val result = mutableSetOf<String>()
            File(fileName).takeIf { it.exists() }?.useLines { lines ->
                lines.forEach { line ->
                    result.add(line)
                }
            }
            return result
        }

        private fun writeToFile(fileName: String, list: List<String>) {
            val file = File(fileName)
            file.takeIf { it.exists() || it.createNewFile() }?.writeText(
                list.reduceOrNull { acc, item ->
                    "$acc\n$item"
                } ?: "",
                charset = Charset.forName("utf-8")
            )
        }

        private fun appendToFile(fileName: String, list: List<String>): Boolean {
            val file = File(fileName)
            return file.takeIf { it.exists() || it.createNewFile() }?.appendText(
                list.reduce { acc, item ->
                    "$acc\n$item"
                },
                charset = Charset.forName("utf-8")
            ) != null
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun currentTime(): String {
    return "${LocalDateTime.now()}"
}

fun Request.Builder.addHeaders(newHeaders: Map<String, String>): Request.Builder {
    newHeaders.forEach { (name, value) ->
        this.addHeader(name, value)
    }
    return this
}

fun String.getElementsByTag(tagName: String): Elements {
    val document = Jsoup.parse(this)
    return document.html(this).getElementsByTag(tagName)
}

fun main() = runBlocking {
    val downloader = XSDownloader()
    val host = "https://www.qishuta.org"
    val url = "/"
//    downloader.download(host = host, url = url)
    downloader.sortFiles()
    Unit
}