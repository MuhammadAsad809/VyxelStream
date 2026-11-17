package com.primevision.app.data.plugin

import android.content.Context
import android.util.Log
import com.lagradost.cloudstream3.MainAPI
import com.lagradost.cloudstream3.MovieSearchResponse
import com.lagradost.cloudstream3.SearchResponse
import com.lagradost.cloudstream3.plugins.Plugin
import com.lagradost.cloudstream3.plugins.PluginManager
import com.primevision.app.data.model.Movie
import dalvik.system.DexClassLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.zip.ZipFile

object Cs3PluginLoader {
    private const val TAG = "Cs3PluginLoader"

    data class PluginInfo(
        val id: String,
        val name: String,
        val version: Int,
        val className: String,
        val sourcePath: String,
        val isLoaded: Boolean,
        val statusMessage: String = "Active"
    )

    private val _plugins = MutableStateFlow<List<PluginInfo>>(emptyList())
    val plugins: StateFlow<List<PluginInfo>> = _plugins.asStateFlow()

    /**
     * Initializes bundled plugins and scans storage for .cs3 files
     */
    suspend fun initialize(context: Context) = withContext(Dispatchers.IO) {
        val loadedList = mutableListOf<PluginInfo>()

        // 1. Load bundled plugins from assets/plugins/
        try {
            val assetPlugins = context.assets.list("plugins") ?: emptyArray()
            val pluginsDir = File(context.filesDir, "installed_plugins").apply { mkdirs() }

            for (assetName in assetPlugins) {
                if (assetName.endsWith(".cs3")) {
                    val targetFile = File(pluginsDir, assetName)
                    if (!targetFile.exists() || targetFile.length() == 0L) {
                        context.assets.open("plugins/$assetName").use { input ->
                            FileOutputStream(targetFile).use { output ->
                                input.copyTo(output)
                            }
                        }
                    }
                    val info = loadCs3File(context, targetFile)
                    if (info != null) {
                        loadedList.add(info)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading bundled asset plugins", e)
        }

        // 2. Scan external /sdcard/Download/ for user-provided .cs3 files
        val scanDirs = listOf(
            File("/sdcard/Download"),
            File("/sdcard/Download/cs3"),
            File("/storage/emulated/0/Download"),
            File(context.filesDir, "installed_plugins")
        )

        for (dir in scanDirs) {
            if (dir.exists() && dir.isDirectory) {
                dir.listFiles { file -> file.extension.equals("cs3", ignoreCase = true) }?.forEach { cs3File ->
                    if (loadedList.none { it.sourcePath == cs3File.absolutePath }) {
                        val info = loadCs3File(context, cs3File)
                        if (info != null) {
                            loadedList.add(info)
                        }
                    }
                }
            }
        }

        _plugins.value = loadedList
        Log.i(TAG, "Cs3PluginLoader initialization complete. Loaded ${loadedList.count { it.isLoaded }} extensions.")
    }

    /**
     * Dynamically loads a .cs3 archive using Dalvik DexClassLoader
     */
    fun loadCs3File(context: Context, cs3File: File): PluginInfo? {
        if (!cs3File.exists() || cs3File.length() == 0L) {
            return null
        }

        val pluginId = cs3File.nameWithoutExtension
        val workDir = File(context.cacheDir, "dex_cache/$pluginId").apply { mkdirs() }
        val extractedDex = File(workDir, "classes.dex")
        val optDir = File(workDir, "opt").apply { mkdirs() }

        var pluginClassName = ""
        var pluginName = pluginId
        var pluginVersion = 1

        try {
            // Unzip manifest.json and classes.dex
            ZipFile(cs3File).use { zip ->
                val manifestEntry = zip.getEntry("manifest.json")
                if (manifestEntry != null) {
                    val manifestStr = zip.getInputStream(manifestEntry).bufferedReader().use { it.readText() }
                    val json = JSONObject(manifestStr)
                    pluginClassName = json.optString("pluginClassName", "")
                    pluginName = json.optString("name", pluginId)
                    pluginVersion = json.optInt("version", 1)
                }

                val dexEntry = zip.getEntry("classes.dex") ?: return null
                if (!extractedDex.exists() || extractedDex.length() != dexEntry.size) {
                    zip.getInputStream(dexEntry).use { input ->
                        FileOutputStream(extractedDex).use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            }

            if (pluginClassName.isEmpty()) {
                pluginClassName = "com.$pluginId.${pluginId}Plugin"
            }

            // Create Dalvik DexClassLoader linked to app's parent class loader
            val classLoader = DexClassLoader(
                extractedDex.absolutePath,
                optDir.absolutePath,
                null,
                context.classLoader
            )

            // Load and instantiate plugin class
            val loadedClass = classLoader.loadClass(pluginClassName)
            val instance = loadedClass.getDeclaredConstructor().newInstance()

            if (instance is Plugin) {
                instance.load(context)
                Log.i(TAG, "Successfully invoked Plugin.load() for $pluginName ($pluginClassName)")
            } else if (instance is MainAPI) {
                PluginManager.registerMainAPI(instance)
                Log.i(TAG, "Successfully registered MainAPI for $pluginName ($pluginClassName)")
            }

            return PluginInfo(
                id = pluginId,
                name = pluginName,
                version = pluginVersion,
                className = pluginClassName,
                sourcePath = cs3File.absolutePath,
                isLoaded = true,
                statusMessage = "Loaded & Ready"
            )

        } catch (e: Throwable) {
            Log.e(TAG, "Failed to load extension $pluginId from ${cs3File.absolutePath}", e)
            return PluginInfo(
                id = pluginId,
                name = pluginName,
                version = pluginVersion,
                className = pluginClassName,
                sourcePath = cs3File.absolutePath,
                isLoaded = false,
                statusMessage = "Failed: ${e.message}"
            )
        }
    }

    /**
     * Executes real search across all loaded .cs3 providers
     */
    suspend fun searchAllProviders(query: String): List<Movie> = withContext(Dispatchers.IO) {
        val results = mutableListOf<Movie>()
        val apis = PluginManager.getMainAPIs()

        for (api in apis) {
            try {
                val searchResponses = api.search(query)
                for (resp in searchResponses) {
                    val year = (resp as? MovieSearchResponse)?.year?.toString() ?: "2024"
                    val quality = resp.quality?.name ?: "HD"
                    results.add(
                        Movie(
                            id = "ext_${api.name}_${resp.url.hashCode()}",
                            title = resp.name,
                            posterUrl = resp.posterUrl ?: "https://image.tmdb.org/t/p/w780/d5NXSklXo0qyIYkgV94XAgMIckC.jpg",
                            backdropUrl = resp.posterUrl ?: "https://image.tmdb.org/t/p/original/lzWHmY21F0VwQ048IRSc7Z84q4.jpg",
                            year = year,
                            duration = "1h 45m",
                            rating = "8.2",
                            quality = quality,
                            synopsis = "Live streaming stream provided by ${api.name} extension.",
                            platform = api.name
                        )
                    )
                }
            } catch (e: Throwable) {
                Log.e(TAG, "Error querying provider ${api.name} for query '$query'", e)
            }
        }
        results
    }
}
