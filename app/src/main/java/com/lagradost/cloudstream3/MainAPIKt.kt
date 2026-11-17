package com.lagradost.cloudstream3

import android.util.Base64
import java.nio.charset.StandardCharsets

object MainAPIKt {

    @JvmStatic
    fun mainPageOf(vararg pairs: Pair<String, String>): List<MainPageData> {
        return pairs.map { (url, name) -> MainPageData(data = url, name = name) }
    }

    @JvmStatic
    fun base64Decode(value: String): String {
        return try {
            val bytes = Base64.decode(value, Base64.DEFAULT)
            String(bytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            value
        }
    }

    @JvmStatic
    fun base64Encode(value: String): String {
        return try {
            Base64.encodeToString(value.toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP)
        } catch (e: Exception) {
            value
        }
    }

    @JvmStatic
    fun newMovieSearchResponse(
        name: String,
        url: String,
        type: TvType = TvType.Movie,
        initializer: MovieSearchResponse.() -> Unit = {}
    ): MovieSearchResponse {
        val resp = MovieSearchResponse(name = name, url = url, type = type)
        resp.initializer()
        return resp
    }

    @JvmStatic
    fun newTvSeriesSearchResponse(
        name: String,
        url: String,
        type: TvType = TvType.TvSeries,
        initializer: TvSeriesSearchResponse.() -> Unit = {}
    ): TvSeriesSearchResponse {
        val resp = TvSeriesSearchResponse(name = name, url = url, type = type)
        resp.initializer()
        return resp
    }

    @JvmStatic
    fun newMovieLoadResponse(
        name: String,
        url: String,
        type: TvType = TvType.Movie,
        dataUrl: String = "",
        initializer: MovieLoadResponse.() -> Unit = {}
    ): MovieLoadResponse {
        val resp = MovieLoadResponse(name = name, url = url, type = type, dataUrl = dataUrl)
        resp.initializer()
        return resp
    }

    @JvmStatic
    fun newTvSeriesLoadResponse(
        name: String,
        url: String,
        type: TvType = TvType.TvSeries,
        dataUrl: String = "",
        initializer: TvSeriesLoadResponse.() -> Unit = {}
    ): TvSeriesLoadResponse {
        val resp = TvSeriesLoadResponse(name = name, url = url, type = type, dataUrl = dataUrl)
        resp.initializer()
        return resp
    }
}
