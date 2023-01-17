package com.maverick.splash.api

import com.maverick.splash.api.ApiUtility.Companion.API_KEY
import com.maverick.splash.model.ImageModel
import com.maverick.splash.model.UrlModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {

    @Headers("Authorization: Client-ID "+API_KEY)
    @GET("/photos")
    suspend fun getImages(): Response<List<ImageModel>>

    @Headers("Authorization: Client-ID "+API_KEY)
    @GET("/users/{username}/photos")
    suspend fun getUser(@Path("username") user:String): Response<List<ImageModel>>
}