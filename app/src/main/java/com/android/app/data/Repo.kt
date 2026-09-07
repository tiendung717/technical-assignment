package com.android.app.data

import com.android.app.data.api.service.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(
    private val apiService: ApiService
) {

}