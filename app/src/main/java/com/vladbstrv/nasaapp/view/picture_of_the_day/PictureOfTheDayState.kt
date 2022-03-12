package com.vladbstrv.nasaapp.view.picture_of_the_day

import com.vladbstrv.nasaapp.remote_repository.DTO.PictureOfTheDayDTO

sealed class PictureOfTheDayState {
    data class Success(val serverResponseData: PictureOfTheDayDTO) : PictureOfTheDayState()
    data class Error(val error: Throwable) : PictureOfTheDayState()
    data class Loading(val progress: Int?) : PictureOfTheDayState()
}
