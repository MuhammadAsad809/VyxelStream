package com.lagradost.cloudstream3

import java.io.Serializable

interface SearchResponse : Serializable {
    val name: String
    val url: String
    val apiName: String
    val type: TvType?
    var posterUrl: String?
    var id: Int?
    var quality: SearchQuality?
}

data class MovieSearchResponse(
    override val name: String,
    override val url: String,
    override val apiName: String = "",
    override val type: TvType? = TvType.Movie,
    override var posterUrl: String? = null,
    override var id: Int? = null,
    override var quality: SearchQuality? = null,
    var year: Int? = null
) : SearchResponse

data class TvSeriesSearchResponse(
    override val name: String,
    override val url: String,
    override val apiName: String = "",
    override val type: TvType? = TvType.TvSeries,
    override var posterUrl: String? = null,
    override var id: Int? = null,
    override var quality: SearchQuality? = null,
    var year: Int? = null,
    var latestEpisode: String? = null
) : SearchResponse

data class AnimeSearchResponse(
    override val name: String,
    override val url: String,
    override val apiName: String = "",
    override val type: TvType? = TvType.Anime,
    override var posterUrl: String? = null,
    override var id: Int? = null,
    override var quality: SearchQuality? = null,
    var year: Int? = null
) : SearchResponse

data class SearchResponseList(
    val list: List<SearchResponse> = emptyList()
) : Serializable

data class HomePageResponse(
    val items: List<HomePageList> = emptyList()
) : Serializable

data class HomePageList(
    val name: String,
    val list: List<SearchResponse>
) : Serializable

data class MainPageData(
    val data: String,
    val name: String,
    val horizontalImages: Boolean = false
) : Serializable

data class MainPageRequest(
    val name: String,
    val data: String,
    val horizontalImages: Boolean = false
) : Serializable

open class LoadResponse(
    open val name: String,
    open val url: String,
    open val apiName: String,
    open val type: TvType,
    open val dataUrl: String,
    open var posterUrl: String? = null,
    open var year: Int? = null,
    open var plot: String? = null,
    open var rating: Int? = null,
    open var tags: List<String>? = null,
    open var duration: Int? = null
) : Serializable

data class MovieLoadResponse(
    override val name: String,
    override val url: String,
    override val apiName: String = "",
    override val type: TvType = TvType.Movie,
    override val dataUrl: String = "",
    override var posterUrl: String? = null,
    override var year: Int? = null,
    override var plot: String? = null,
    override var rating: Int? = null,
    override var tags: List<String>? = null,
    override var duration: Int? = null
) : LoadResponse(name, url, apiName, type, dataUrl, posterUrl, year, plot, rating, tags, duration)

data class TvSeriesLoadResponse(
    override val name: String,
    override val url: String,
    override val apiName: String = "",
    override val type: TvType = TvType.TvSeries,
    override val dataUrl: String = "",
    override var posterUrl: String? = null,
    override var year: Int? = null,
    override var plot: String? = null,
    override var rating: Int? = null,
    override var tags: List<String>? = null,
    override var duration: Int? = null,
    var episodes: List<Episode> = emptyList()
) : LoadResponse(name, url, apiName, type, dataUrl, posterUrl, year, plot, rating, tags, duration)

data class Episode(
    val data: String,
    val name: String? = null,
    val season: Int? = null,
    val episode: Int? = null,
    val posterUrl: String? = null,
    val rating: Int? = null,
    val description: String? = null
) : Serializable

data class SubtitleFile(
    val lang: String,
    val url: String
) : Serializable

data class Actor(
    val name: String,
    val image: String? = null
) : Serializable

enum class ActorRole {
    Main,
    Supporting,
    Background
}

data class ActorData(
    val actor: Actor,
    val role: ActorRole? = null
) : Serializable

data class Score(
    val score: Double,
    val max: Double = 10.0
) : Serializable
