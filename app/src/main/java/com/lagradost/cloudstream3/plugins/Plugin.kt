package com.lagradost.cloudstream3.plugins

import android.content.Context
import com.lagradost.cloudstream3.MainAPI
import com.lagradost.cloudstream3.utils.ExtractorApi

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CloudstreamPlugin

open class Plugin {
    companion object {
        const val $stable = 8
    }

    var openSettings: ((Context) -> Unit)? = null
    var resources: Any? = null
    var filename: String? = null

    open fun load(context: Context? = null) {}

    fun registerMainAPI(api: MainAPI) {
        PluginManager.registerMainAPI(api)
    }

    fun registerExtractorAPI(api: ExtractorApi) {
        PluginManager.registerExtractorAPI(api)
    }
}

object PluginManager {
    private val mainApis = mutableListOf<MainAPI>()
    private val extractorApis = mutableListOf<ExtractorApi>()

    @Synchronized
    fun registerMainAPI(api: MainAPI) {
        if (mainApis.none { it.name == api.name }) {
            mainApis.add(api)
        }
    }

    @Synchronized
    fun registerExtractorAPI(api: ExtractorApi) {
        if (extractorApis.none { it.name == api.name }) {
            extractorApis.add(api)
        }
    }

    @Synchronized
    fun getMainAPIs(): List<MainAPI> = mainApis.toList()

    @Synchronized
    fun getExtractorAPIs(): List<ExtractorApi> = extractorApis.toList()

    @Synchronized
    fun clear() {
        mainApis.clear()
        extractorApis.clear()
    }
}
