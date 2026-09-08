package com.android.app.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.security.SecureRandom

private const val SIGNATURE_METHOD = "PLAINTEXT"
private const val OAUTH_VERSION = "1.0"
private const val NONCE_BYTES = 16

class OAuthInterceptor(
    private val consumerKey: String,
    private val consumerSecret: String
) : Interceptor {

    private val random = SecureRandom()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header("Authorization", authorizationHeader())
            .build()

        return chain.proceed(request)
    }

    private fun authorizationHeader(): String {
        val parameters = linkedMapOf(
            "oauth_consumer_key" to consumerKey,
            "oauth_signature_method" to SIGNATURE_METHOD,
            "oauth_signature" to "${consumerSecret.percentEncode()}&",
            "oauth_timestamp" to (System.currentTimeMillis() / 1000).toString(),
            "oauth_nonce" to nonce(),
            "oauth_version" to OAUTH_VERSION
        )

        return parameters.entries.joinToString(prefix = "OAuth ", separator = ", ") { entry ->
            "${entry.key.percentEncode()}=\"${entry.value}\""
        }
    }

    private fun nonce(): String {
        val bytes = ByteArray(NONCE_BYTES)
        random.nextBytes(bytes)
        return bytes.joinToString("") { byte -> "%02x".format(byte.toInt() and 0xFF) }
    }
}

private fun String.percentEncode(): String = buildString {
    for (byte in this@percentEncode.toByteArray(Charsets.UTF_8)) {
        val code = byte.toInt() and 0xFF
        val char = code.toChar()
        if (char in 'A'..'Z' || char in 'a'..'z' || char in '0'..'9' || char in "-._~") {
            append(char)
        } else {
            append("%%%02X".format(code))
        }
    }
}
