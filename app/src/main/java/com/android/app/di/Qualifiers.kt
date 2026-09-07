package com.android.app.di

import javax.inject.Qualifier

/**
 * Marks the OkHttp interceptor that logs request and response bodies.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingQualifier

/**
 * Marks the OkHttp interceptor that attaches the bearer token to outgoing requests.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthQualifier
