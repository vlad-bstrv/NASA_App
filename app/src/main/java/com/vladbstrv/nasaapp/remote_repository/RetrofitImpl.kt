package com.vladbstrv.nasaapp.remote_repository

import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.google.gson.GsonBuilder
import com.vladbstrv.nasaapp.remote_repository.DTO.MarsDTO.MarsDTO
import com.vladbstrv.nasaapp.remote_repository.DTO.PictureOfTheDayDTO
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {

    private val baseUrl = "https://api.nasa.gov/"

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(RetrofitApi::class.java)
    }

    fun getPictureOfTheDay(apiKey: String, date: String, podCallBack: Callback<PictureOfTheDayDTO>) {
        api.getPictureOfTheDay(apiKey, date).enqueue(podCallBack)
    }

    fun getMarsPictureByDate(earth_date: String, apiKey: String, marsCallbackByDate: Callback<MarsDTO>) {
        api.getMarsImageByDate(earth_date, apiKey).enqueue(marsCallbackByDate)
    }

    fun getEPIC(apiKey: String, epicCallback: Callback<List<EarthEpicServerResponseData>>) {
        api.getEPIC(apiKey).enqueue(epicCallback)
    }

}