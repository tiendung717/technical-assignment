package com.android.app.data.api.authenticator

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Authenticator for OkHttp that handles authentication failures (401 responses).
 * Attempts to refresh the authentication token and retry the request.
 *
 * @param tokenRefresher A function that attempts to refresh the authentication token.
 *                       Returns the new token if successful, null otherwise.
 */
class TokenAuthenticator(
    private val tokenRefresher: suspend () -> String?
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // Avoid infinite retry loop - if we already tried to authenticate, give up
        if (response.request.header("Authorization") != null &&
            response.priorResponse?.code == 401) {
            return null // Already attempted authentication, stop retrying
        }

        // Try to refresh the token synchronously
        // Note: In production, you might want to use a blocking call or coroutine bridge here
        val newToken = runCatching {
            // This is a simplified version - in production you'd need proper coroutine handling
            kotlinx.coroutines.runBlocking {
                tokenRefresher()
            }
        }.getOrNull()

        return if (newToken != null) {
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        } else {
            null // Unable to refresh token, give up
        }
    }
}

