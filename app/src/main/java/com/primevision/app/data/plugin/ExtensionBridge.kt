package com.primevision.app.data.plugin

import android.content.Context
import com.primevision.app.data.model.Movie
import kotlinx.coroutines.flow.StateFlow

/**
 * ExtensionBridge interfaces between PrimeVision UI and the dynamic .cs3 runtime.
 */
object ExtensionBridge {

    val installedPlugins: StateFlow<List<Cs3PluginLoader.PluginInfo>>
        get() = Cs3PluginLoader.plugins

    suspend fun initialize(context: Context) {
        Cs3PluginLoader.initialize(context)
    }

    suspend fun search(query: String): List<Movie> {
        return Cs3PluginLoader.searchAllProviders(query)
    }
}
