package com.yzh.demoapp.aacell.repository

import com.yzh.demoapp.base.network.okHttpClient
import okhttp3.OkHttpClient

class AACellRepository(
    private val client: OkHttpClient = okHttpClient,
) : AACellRoomRepository by AACellRoomRepositoryImpl(client) {

}
