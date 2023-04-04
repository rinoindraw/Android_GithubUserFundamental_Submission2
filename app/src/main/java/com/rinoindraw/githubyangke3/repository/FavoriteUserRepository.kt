package com.rinoindraw.githubyangke3.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.rinoindraw.githubyangke3.database.FavoriteUser
import com.rinoindraw.githubyangke3.database.FavoriteUserDao
import com.rinoindraw.githubyangke3.database.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllUser()

    fun insert(user: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insertFavorite(user) }
    }

    fun delete(id: Int) {
        executorService.execute { mFavoriteUserDao.removeFavorite(id) }
    }
}