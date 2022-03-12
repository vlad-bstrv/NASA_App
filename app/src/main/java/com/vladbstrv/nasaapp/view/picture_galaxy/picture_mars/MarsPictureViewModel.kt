package com.vladbstrv.nasaapp.view.picture_galaxy.picture_mars

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vladbstrv.nasaapp.BuildConfig
import com.vladbstrv.nasaapp.remote_repository.DTO.MarsDTO.MarsDTO
import com.vladbstrv.nasaapp.remote_repository.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MarsPictureViewModel(
    private val liveData: MutableLiveData<MarsPictureState> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<MarsPictureState> {
        return liveData
    }

    fun getMarsPicture() {
        liveData.postValue(MarsPictureState.Loading(null))
        val earthDate = getDayBeforeYesterday()
        retrofitImpl.getMarsPictureByDate(earthDate,BuildConfig.NASA_API_KEY, marsCallback)
    }

    fun getDayBeforeYesterday(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(2)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return yesterday.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, -2)
            return s.format(cal.time)
        }
    }

    val marsCallback = object : Callback<MarsDTO> {

        override fun onResponse(
            call: Call<MarsDTO>,
            response: Response<MarsDTO>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveData.postValue(MarsPictureState.Success(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveData.postValue(MarsPictureState.Error(Throwable("Unidentified error")))
                } else {
                    liveData.postValue(MarsPictureState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<MarsDTO>, t: Throwable) {
            liveData.postValue(MarsPictureState.Error(t))
        }
    }
}