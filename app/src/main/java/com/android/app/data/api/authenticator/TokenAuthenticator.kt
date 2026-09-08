package com.android.app.data.api.authenticator

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenRefresher: suspend () -> String?
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Authorization") != null &&
            response.priorResponse?.code == 401) {
            return null
        }

        val newToken = runCatching {
            kotlinx.coroutines.runBlocking {
                tokenRefresher()
            }
        }.getOrNull()

        return if (newToken != null) {
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        } else {
            null
        }
    }
}
