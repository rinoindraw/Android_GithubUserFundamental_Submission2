package com.rinoindraw.githubyangke3.main.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rinoindraw.githubyangke3.database.FavoriteUser
import com.rinoindraw.githubyangke3.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application): ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavorites()
}