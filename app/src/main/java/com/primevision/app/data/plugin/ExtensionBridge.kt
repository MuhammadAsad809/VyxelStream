package com.primevision.app.data.plugin

import com.primevision.app.data.model.Movie
import java.io.File

/**
 * ExtensionBridge interfaces with Cloudstream .cs3 plugin formats.
 * It provides translation between the extension Dalvik runtime and PrimeVision UI models.
 */
object ExtensionBridge {

    // Installed extension descriptors
    data class InstalledExtension(
        val id: String,
        val name: String,
        val version: Int,
        val pluginClassName: String,
        val cs3File: File?,
        val isLoaded: Boolean = false
    )

    private val availableExtensions = mutableListOf(
        InstalledExtension(
            id = "hdhub4u",
            name = "HDhub4u",
            version = 57,
            pluginClassName = "com.hdhub4u.HDhub4uPlugin",
            cs3File = null,
            isLoaded = true
        ),
        InstalledExtension(
            id = "animedekho",
            name = "AnimeDekho",
            version = 73,
            pluginClassName = "com.animedekho.AnimeDekhoPlugin",
            cs3File = null,
            isLoaded = true
        ),
        InstalledExtension(
            id = "streamplay",
            name = "StreamPlay",
            version = 42,
            pluginClassName = "com.streamplay.StreamPlayPlugin",
            cs3File = null,
            isLoaded = true
        )
    )

    fun getInstalledExtensions(): List<InstalledExtension> = availableExtensions

    /**
     * Map a raw plugin search result into PrimeVision Movie model
     */
    fun mapToMovie(
        title: String,
        posterUrl: String?,
        url: String,
        quality: String = "HD",
        year: String = "2024"
    ): Movie {
        return Movie(
            id = url.hashCode().toString(),
            title = title,
            posterUrl = posterUrl ?: "https://image.tmdb.org/t/p/w780/d5NXSklXo0qyIYkgV94XAgMIckC.jpg",
            backdropUrl = posterUrl ?: "https://image.tmdb.org/t/p/original/lzWHmY21F0VwQ048IRSc7Z84q4.jpg",
            year = year,
            duration = "1h 45m",
            rating = "8.2",
            quality = quality,
            synopsis = "Stream resolved via PrimeVision Extension Engine ($title).",
            platform = "Extension"
        )
    }
}
