package com.lagradost.cloudstream3

import com.lagradost.cloudstream3.utils.ExtractorLink

abstract class MainAPI {
    companion object {
        const val $stable = 8
    }

    open var mainUrl: String = ""
    open var name: String = ""
    open var lang: String = "en"
    open val hasMainPage: Boolean = false
    open val hasDownloadSupport: Boolean = true
    open val hasQuickSearch: Boolean = false
    open val supportedTypes: Set<TvType> = setOf(TvType.Movie, TvType.TvSeries)
    open val mainPage: List<MainPageData> = emptyList()

    open suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        return HomePageResponse(emptyList())
    }

    open suspend fun search(query: String): List<SearchResponse> {
        return emptyList()
    }

    open suspend fun search(query: String, page: Int): SearchResponseList {
        val list = search(query)
        return SearchResponseList(list)
    }

    open suspend fun load(url: String): LoadResponse? {
        return null
    }

    open suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        return false
    }
}
