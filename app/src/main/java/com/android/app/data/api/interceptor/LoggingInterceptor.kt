package com.android.app.data.api.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class LoggingInterceptor {

    fun create(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}

