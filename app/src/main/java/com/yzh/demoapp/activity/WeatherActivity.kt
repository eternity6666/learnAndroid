/*
 * CopyRight (C) 2023 Tencent. All rights reserved.
 */
package com.yzh.demoapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.SerializedName
import com.yzh.annotation.YActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.text.SimpleDateFormat
import java.util.Date

/**
 * @author baronyang@tencent.com
 * @since 2023/2/2 16:16
 */
@YActivity(title = "天气", description = "retrofit + compose + flow")
class WeatherActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        setContent {
            MaterialTheme {
                WeatherPage(viewModel)
            }
        }
    }

}

class WeatherViewModel : ViewModel() {
    private var _weatherData = MutableStateFlow<WeatherData?>(null)
    val weatherData: Flow<WeatherData?> = _weatherData

    fun loadData(string: String, text: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CaiYunAppService::class.java)
        viewModelScope.launch {
            kotlin.runCatching {
                val result = service.getWeatherData(string, text)
                _weatherData.value = result
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}

private const val URL = "https://api.caiyunapp.com/v2.5/"
private const val TOKEN = "jbmH55LQdAJDHkjt"

interface CaiYunAppService {
    @GET("$TOKEN/{longitude},{dimension}/realtime.json")
    suspend fun getWeatherData(
        @Path("longitude") longitude: String,
        @Path("dimension") dimension: String
    ): WeatherData
}

data class WeatherData(
    val location: Array<String>,
    @SerializedName("server_time")
    val serverTime: Long,
    val result: WeatherResult,
)

data class WeatherResult(
    val realtime: Realtime,
)

data class Realtime(
    val temperature: Int,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherPage(viewModel: WeatherViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val text1 = remember { mutableStateOf("121.6544") }
        val text2 = remember { mutableStateOf("25.1552") }
        val weatherData = viewModel.weatherData.collectAsState(initial = null)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
                .align(Alignment.Center)
        ) {
            TextField(
                value = text1.value,
                modifier = Modifier
                    .fillMaxWidth(),
                onValueChange = {
                    text1.value = it
                }
            )
            TextField(
                text2.value,
                modifier = Modifier
                    .fillMaxWidth(),
                onValueChange = {
                    text2.value = it
                }
            )
            Button(onClick = {
                viewModel.loadData(text1.value, text2.value)
            }) {
                Text(text = "查询")
            }
            val data = weatherData.value
            if (data != null) {
                Text(text = "查询时间为${(data.serverTime * 1000).formatTime()}")
                Text(text = "温度: ${data.result.realtime.temperature}")
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Long.formatTime(): String {
    val format = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss")
    val date = Date(this)
    return format.format(date)
}

