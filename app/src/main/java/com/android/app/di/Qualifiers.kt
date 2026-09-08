package com.android.app.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthQualifier
