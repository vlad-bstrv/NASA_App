package com.vladbstrv.nasaapp.view.picture_galaxy.picture_earth

import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.vladbstrv.nasaapp.remote_repository.DTO.MarsDTO.MarsDTO
import com.vladbstrv.nasaapp.remote_repository.DTO.PictureOfTheDayDTO
import com.vladbstrv.nasaapp.view.picture_of_the_day.PictureOfTheDayState

sealed class EarthPictureState {
    data class Success(val serverResponseData: List<EarthEpicServerResponseData>) : EarthPictureState()
    data class Error(val error: Throwable) : EarthPictureState()
    object Loading : EarthPictureState()
}