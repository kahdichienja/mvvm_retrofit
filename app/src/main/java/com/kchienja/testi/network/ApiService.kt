package com.kchienja.testi.network

import com.kchienja.testi.constants.NetworkConstants
import com.kchienja.testi.model.RepositoriesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(NetworkConstants.REPOSITORY_URL)
    suspend fun getPublicRepositories(@Query("since") since: String): RepositoriesModel
}