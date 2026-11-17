package com.lagradost.cloudstream3.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object AppUtils {
    private val mapper = jacksonObjectMapper()

    @JvmStatic
    fun toJson(obj: Any): String {
        return try {
            mapper.writeValueAsString(obj)
        } catch (e: Exception) {
            "{}"
        }
    }

    inline fun <reified T> parseJson(json: String): T? {
        return try {
            mapper.readValue(json, T::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
