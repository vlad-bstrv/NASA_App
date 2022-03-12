package com.vladbstrv.nasaapp.view.picture_galaxy.picture_mars

import com.vladbstrv.nasaapp.remote_repository.DTO.MarsDTO.MarsDTO
import com.vladbstrv.nasaapp.remote_repository.DTO.PictureOfTheDayDTO
import com.vladbstrv.nasaapp.view.picture_of_the_day.PictureOfTheDayState

sealed class MarsPictureState {
    data class Success(val serverResponseData: MarsDTO) : MarsPictureState()
    data class Error(val error: Throwable) : MarsPictureState()
    data class Loading(val progress: Int?) : MarsPictureState()
}