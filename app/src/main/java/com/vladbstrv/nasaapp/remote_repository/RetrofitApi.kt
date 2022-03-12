package com.vladbstrv.nasaapp.remote_repository

import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.vladbstrv.nasaapp.remote_repository.DTO.MarsDTO.MarsDTO
import com.vladbstrv.nasaapp.remote_repository.DTO.PictureOfTheDayDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Call<PictureOfTheDayDTO>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getPictureOfMars(
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String
    ): Call<MarsDTO>

    @GET("EPIC/api/natural")
    fun getEPIC(
        @Query("api_key") apiKey: String,
    ): Call<List<EarthEpicServerResponseData>>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsImageByDate(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsDTO>
}