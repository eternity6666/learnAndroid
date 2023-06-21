package com.yzh.demoapp.aacell.repository

import android.util.Log
import com.yzh.demoapp.aacell.model.RoomType
import com.yzh.demoapp.base.data.toTyped
import com.yzh.demoapp.base.network.ResponseData
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

interface AACellRoomRepository {
    suspend fun createRoom(
        roomId: String = "",
        adminWord: String = "",
        password: String = "",
    ): Boolean

    suspend fun randomRoom(): String

    suspend fun fetchRoomType(
        roomId: String
    ): RoomType
}

class AACellRoomRepositoryImpl(private val client: OkHttpClient) : AACellRoomRepository {

    override suspend fun createRoom(
        roomId: String,
        adminWord: String,
        password: String,
    ): Boolean {
        val requestBody = FormBody.Builder().apply {
            add(ROOM_ID, roomId)
            add(ADMIN_WORD, adminWord)
            add(PASSWORD, password)
        }.build()
        val request = Request.Builder()
            .url(URL_CREATE)
            .post(requestBody)
            .build()
        val response = runCatching {
            client.newCall(request).execute()
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()
        val responseBody = response?.body?.string().orEmpty()
        val code = responseBody.toTyped<ResponseData<String>>()?.code ?: -1
        return code == CODE_OK
    }

    override suspend fun randomRoom(): String {
        val response = client.newCall(
            Request.Builder()
                .url("$BASE_URL/room/random")
                .build()
        ).execute()
        Log.i(TAG, "randomRoom: ${response.body?.string()}")
        return ""
    }

    override suspend fun fetchRoomType(roomId: String): RoomType {
        if (roomId.isEmpty()) {
            return RoomType.Unknown
        }
        val response = client.newCall(
            Request.Builder()
                .url("$BASE_URL/$roomId")
                .build()
        ).execute()
        val responseString = response.body?.string().orEmpty()
        val title = ROOM_TYPE_PATTERN.find(responseString)
            ?.groupValues?.getOrNull(1)
            ?.substringAfter(ROOM_TYPE_PATTERN_PREFIX)
            ?.substringBefore(ROOM_TYPE_PATTERN_SUFFIX).orEmpty()
        return RoomType.match(title)
    }

    companion object {
        private const val TAG = "AACellRoomRepositoryImpl"
        private const val CODE_OK = 0
        private const val PASSWORD = "password"
        private const val BASE_URL = "https://aacell.me"
        private const val URL_CREATE = "$BASE_URL/room/create"
        private const val ROOM_TYPE_PATTERN_PREFIX = "<title>"
        private const val ROOM_TYPE_PATTERN_SUFFIX = "</title>"

        private val ROOM_ID = "roomId".lowercase()
        private val ADMIN_WORD = "adminWord".lowercase()
        private val ROOM_TYPE_PATTERN = Regex(
            "$ROOM_TYPE_PATTERN_PREFIX(.*)$ROOM_TYPE_PATTERN_SUFFIX"
        )
    }
}
