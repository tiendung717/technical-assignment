package com.android.app.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor that adds authentication headers to HTTP requests.
 *
 * @param tokenProvider A function that provides the current authentication token.
 *                      Returns null if no token is available.
 */
class AuthenticationInterceptor(
    private val tokenProvider: () -> String?
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenProvider()

        val request = if (token != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(request)
    }
}

