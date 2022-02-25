package com.vladbstrv.nasaapp.view.picture_of_the_day

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vladbstrv.nasaapp.BuildConfig
import com.vladbstrv.nasaapp.remote_repository.PictureOfTheDayDTO
import com.vladbstrv.nasaapp.remote_repository.PictureOfTheDayRemoteRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayState> = MutableLiveData(),
    private val pictureOfTheDayRemoteRepositoryImpl: PictureOfTheDayRemoteRepositoryImpl = PictureOfTheDayRemoteRepositoryImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheDayState> {
        return liveData
    }

    fun sendServerRequest() {
        liveData.postValue(PictureOfTheDayState.Loading(null))
        pictureOfTheDayRemoteRepositoryImpl.getRetrofitImpl()
            .getPictureOfTheDay(BuildConfig.NASA_API_KEY).enqueue(
                object : Callback<PictureOfTheDayDTO> {
                    override fun onResponse(
                        call: Call<PictureOfTheDayDTO>,
                        response: Response<PictureOfTheDayDTO>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.let {
                                liveData.postValue(PictureOfTheDayState.Success(it))
                            }
                        } else {
                            Log.e("onResponseForSendServerRequest", response.errorBody().toString())
                        }
                    }

                    override fun onFailure(call: Call<PictureOfTheDayDTO>, t: Throwable) {
                        liveData.postValue(PictureOfTheDayState.Error(t))
                        Log.e("failureForSendServerRequest", t.message.toString())
                    }

                }
            )
    }

    fun sendServerRequestForDate(date: Int) {
        liveData.postValue(PictureOfTheDayState.Loading(null))
        pictureOfTheDayRemoteRepositoryImpl.getRetrofitImpl()
            .getPictureOfTheDayForDate(BuildConfig.NASA_API_KEY, getDate(date)).enqueue(
                object : Callback<PictureOfTheDayDTO> {
                    override fun onResponse(
                        call: Call<PictureOfTheDayDTO>,
                        response: Response<PictureOfTheDayDTO>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.let {
                                liveData.postValue(PictureOfTheDayState.Success(it))
                            }
                        } else {
                            Log.e(
                                "onResponseForSendServerRequestForDate",
                                response.errorBody().toString()
                            )

                        }
                    }

                    override fun onFailure(call: Call<PictureOfTheDayDTO>, t: Throwable) {
                        liveData.postValue(PictureOfTheDayState.Error(t))
                        Log.e("failureForSendServerRequestForDate", t.message.toString())
                    }

                }
            )
    }

    private fun getDate(day: Int): String {
        val cal = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        cal.add(Calendar.DATE, -day + 1)
        return simpleDateFormat.format(cal.time)
    }
}