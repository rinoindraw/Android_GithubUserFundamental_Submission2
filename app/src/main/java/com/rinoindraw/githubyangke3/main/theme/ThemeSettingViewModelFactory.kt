package com.rinoindraw.githubyangke3.main.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rinoindraw.githubyangke3.main.splash.SplashScreenViewModel
import com.rinoindraw.githubyangke3.main.MainViewModel

class ThemeSettingViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeSettingViewModel::class.java)) {
            return ThemeSettingViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
            //
        } else if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}