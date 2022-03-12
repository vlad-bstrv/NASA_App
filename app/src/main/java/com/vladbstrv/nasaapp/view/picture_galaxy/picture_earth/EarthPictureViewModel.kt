package com.vladbstrv.nasaapp.view.picture_galaxy.picture_earth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.vladbstrv.nasaapp.BuildConfig
import com.vladbstrv.nasaapp.remote_repository.RetrofitImpl
import com.vladbstrv.nasaapp.view.picture_galaxy.picture_mars.MarsPictureState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthPictureViewModel(
    private val liveDataToObserve: MutableLiveData<EarthPictureState> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl(),
) : ViewModel() {

    fun getLiveData(): MutableLiveData<EarthPictureState> {
        return liveDataToObserve
    }

    fun getEpic() {
        liveDataToObserve.postValue(EarthPictureState.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if(apiKey.isBlank()) {
            EarthPictureState.Error(Throwable("You need API Key"))
        } else {
            retrofitImpl.getEPIC(apiKey, epicCallback )
        }
    }

    private val epicCallback = object : Callback<List<EarthEpicServerResponseData>> {

        override fun onResponse(
            call: Call<List<EarthEpicServerResponseData>>,
            response: Response<List<EarthEpicServerResponseData>>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(EarthPictureState.Success(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(EarthPictureState.Error(Throwable("Unidentified error")))
                } else {
                    liveDataToObserve.postValue(EarthPictureState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            liveDataToObserve.postValue(EarthPictureState.Error(t))
        }
    }
}