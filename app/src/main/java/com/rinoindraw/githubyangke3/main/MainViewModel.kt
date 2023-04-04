package com.rinoindraw.githubyangke3.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rinoindraw.githubyangke3.api.ApiConfig
import com.rinoindraw.githubyangke3.GithubUser
import com.rinoindraw.githubyangke3.UserResponse
import com.rinoindraw.githubyangke3.main.theme.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel( private val pref: SettingPreferences) : ViewModel() {

    private val _listGithubUser = MutableLiveData<List<GithubUser>>()
    val listGithubUser: LiveData<List<GithubUser>> = _listGithubUser

    fun setSearchUsers(query: String) {
        ApiConfig.getApiService()
            .getUserList(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _listGithubUser.value = response.body()?.githubUsers

                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {

                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

}

