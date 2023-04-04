package com.rinoindraw.githubyangke3.main.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rinoindraw.githubyangke3.response.DetailResponse
import com.rinoindraw.githubyangke3.api.ApiConfig
import com.rinoindraw.githubyangke3.database.FavoriteUser
import com.rinoindraw.githubyangke3.repository.FavoriteUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {
    private val _listDetail = MutableLiveData<DetailResponse>()
    val listDetail: LiveData<DetailResponse> = _listDetail

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun insert(user: FavoriteUser) {
        mFavoriteUserRepository.insert(user)
    }

    fun delete(id: Int) {
        mFavoriteUserRepository.delete(id)
    }

    fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavorites()

    fun setUserDetail(username: String) {
        ApiConfig.getApiService()
            .getUsersDetail(username)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) {
                        _listDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }

            })
    }
}