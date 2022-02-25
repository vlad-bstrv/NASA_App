package com.vladbstrv.nasaapp.remote_repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PictureOfTheDayDTO>

    @GET("planetary/apod")
    fun getPictureOfTheDayForDate(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Call<PictureOfTheDayDTO>
}