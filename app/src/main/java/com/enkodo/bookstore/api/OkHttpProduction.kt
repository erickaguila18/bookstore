package com.enkodo.bookstore.api

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

class OkHttpProduction {
    companion object HttpClient {
        private const val DISK_CACHE_SIZE = 50 * 1024 * 1024 // 50MB
        fun getOkHttpClient(context: Context, debug: Boolean): OkHttpClient {
            val cacheDir = File(context.cacheDir, "http")
            val cache = Cache(cacheDir, DISK_CACHE_SIZE.toLong())

            val builder = OkHttpClient.Builder().cache(cache)
            builder.connectTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES)

            if (debug) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }
            return builder.build()
        }
    }

}