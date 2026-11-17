package com.lagradost.cloudstream3.utils

import com.lagradost.cloudstream3.SubtitleFile
import java.io.Serializable

open class ExtractorApi {
    companion object {
        const val `$stable` = 8
    }

    open val name: String = ""
    open val mainUrl: String = ""
    open val requiresReferer: Boolean = false

    open suspend fun getUrl(
        url: String,
        referer: String? = null,
        subtitleCallback: (SubtitleFile) -> Unit = {},
        callback: (ExtractorLink) -> Unit = {}
    ) {
    }
}

data class ExtractorLink(
    val source: String,
    val name: String,
    val url: String,
    val referer: String,
    val quality: Int,
    val isM3u8: Boolean = false,
    val headers: Map<String, String> = emptyMap()
) : Serializable

object ExtractorApiKt {
    const val QUAL_UNKNOWN = 0
    const val QUAL_360 = 360
    const val QUAL_480 = 480
    const val QUAL_720 = 720
    const val QUAL_1080 = 1080
    const val QUAL_4K = 2160

    @JvmStatic
    suspend fun loadExtractor(
        url: String,
        referer: String? = null,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        return false
    }
}
