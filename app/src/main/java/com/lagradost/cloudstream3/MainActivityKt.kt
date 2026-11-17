package com.lagradost.cloudstream3

import com.lagradost.nicehttp.Requests
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object MainActivityKt {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .followRedirects(true)
        .build()

    private val app = Requests(client)

    @JvmStatic
    fun getApp(): Requests = app
}
