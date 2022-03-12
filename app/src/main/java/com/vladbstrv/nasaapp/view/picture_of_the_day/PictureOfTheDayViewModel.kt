package com.vladbstrv.nasaapp.view.picture_of_the_day

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vladbstrv.nasaapp.BuildConfig
import com.vladbstrv.nasaapp.remote_repository.DTO.PictureOfTheDayDTO
import com.vladbstrv.nasaapp.remote_repository.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayState> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheDayState> {
        return liveData
    }

    fun sendServerRequest(day: Int) {
        val date = getDate(day)
        liveData.postValue(PictureOfTheDayState.Loading(null))
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayState.Error(Throwable(API_ERROR))
        } else {
            retrofitImpl.getPictureOfTheDay(apiKey, date, PODCallback)
        }

    }

    private val PODCallback = object : Callback<PictureOfTheDayDTO> {

        override fun onResponse(
            call: Call<PictureOfTheDayDTO>,
            response: Response<PictureOfTheDayDTO>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveData.postValue(PictureOfTheDayState.Success(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveData.postValue(PictureOfTheDayState.Error(Throwable(UNKNOWN_ERROR)))
                } else {
                    liveData.postValue(PictureOfTheDayState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayDTO>, t: Throwable) {
            liveData.postValue(PictureOfTheDayState.Error(t))
        }
    }


    private fun getDate(day: Int): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(day.toLong())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return yesterday.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, (-day))
            return s.format(cal.time)
        }
    }

    companion object {
        private const val API_ERROR = "You need API Key"
        private const val UNKNOWN_ERROR = "Unidentified error"
    }
}