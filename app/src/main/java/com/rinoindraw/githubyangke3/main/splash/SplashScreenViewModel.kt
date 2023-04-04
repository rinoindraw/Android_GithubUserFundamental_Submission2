package com.rinoindraw.githubyangke3.main.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rinoindraw.githubyangke3.main.theme.SettingPreferences

class SplashScreenViewModel(private val pref: SettingPreferences): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}