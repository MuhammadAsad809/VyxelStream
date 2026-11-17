package com.lagradost.nicehttp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.Serializable
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class NiceResponse(
    val response: Response,
    private val client: OkHttpClient
) {
    val body: ResponseBody? get() = response.body
    val text: String by lazy {
        try {
            response.body?.string() ?: ""
        } catch (e: Exception) {
            ""
        }
    }
    val code: Int get() = response.code
    val headers: Headers get() = response.headers
    val url: String get() = response.request.url.toString()

    fun getDocument(): Document {
        return Jsoup.parse(text, url)
    }

    val parser: ResponseParser = object : ResponseParser {
        override fun <T : Any> parseSafe(text: String, kClass: KClass<T>): T? {
            return try {
                val mapper = com.fasterxml.jackson.module.kotlin.jacksonObjectMapper()
                mapper.readValue(text, kClass.java)
            } catch (e: Exception) {
                null
            }
        }
    }
}

interface ResponseParser {
    fun <T : Any> parseSafe(text: String, kClass: KClass<T>): T?
}

class Requests(val client: OkHttpClient = OkHttpClient()) {

    suspend fun get(
        url: String,
        headers: Map<String, String>? = null,
        referer: String? = null,
        params: Map<String, String>? = null,
        cookies: Map<String, String>? = null,
        allowRedirects: Boolean = true,
        cacheTime: Int = 0,
        timeUnit: TimeUnit? = null,
        cacheLimitTime: Long = 0L,
        interceptor: Interceptor? = null,
        verify: Boolean = true,
        parser: ResponseParser? = null
    ): NiceResponse = withContext(Dispatchers.IO) {
        val urlBuilder = url.toHttpUrlOrNull()?.newBuilder() ?: throw IllegalArgumentException("Invalid URL: $url")
        params?.forEach { (k, v) -> urlBuilder.addQueryParameter(k, v) }

        val requestBuilder = Request.Builder().url(urlBuilder.build())
        headers?.forEach { (k, v) -> requestBuilder.header(k, v) }
        referer?.let { requestBuilder.header("Referer", it) }
        cookies?.let {
            val cookieHeader = it.entries.joinToString("; ") { (k, v) -> "$k=$v" }
            requestBuilder.header("Cookie", cookieHeader)
        }

        val effectiveClient = if (allowRedirects) client else client.newBuilder().followRedirects(false).build()
        val response = effectiveClient.newCall(requestBuilder.build()).execute()
        NiceResponse(response, effectiveClient)
    }

    suspend fun post(
        url: String,
        headers: Map<String, String>? = null,
        referer: String? = null,
        params: Map<String, String>? = null,
        cookies: Map<String, String>? = null,
        data: Map<String, String>? = null,
        allowRedirects: Boolean = true,
        cacheTime: Int = 0,
        timeUnit: TimeUnit? = null,
        cacheLimitTime: Long = 0L,
        interceptor: Interceptor? = null,
        verify: Boolean = true,
        parser: ResponseParser? = null
    ): NiceResponse = withContext(Dispatchers.IO) {
        val urlBuilder = url.toHttpUrlOrNull()?.newBuilder() ?: throw IllegalArgumentException("Invalid URL: $url")
        params?.forEach { (k, v) -> urlBuilder.addQueryParameter(k, v) }

        val formBuilder = FormBody.Builder()
        data?.forEach { (k, v) -> formBuilder.add(k, v) }

        val requestBuilder = Request.Builder().url(urlBuilder.build()).post(formBuilder.build())
        headers?.forEach { (k, v) -> requestBuilder.header(k, v) }
        referer?.let { requestBuilder.header("Referer", it) }
        cookies?.let {
            val cookieHeader = it.entries.joinToString("; ") { (k, v) -> "$k=$v" }
            requestBuilder.header("Cookie", cookieHeader)
        }

        val response = client.newCall(requestBuilder.build()).execute()
        NiceResponse(response, client)
    }
}
