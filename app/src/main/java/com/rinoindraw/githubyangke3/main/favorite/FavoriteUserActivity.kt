package com.rinoindraw.githubyangke3.main.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rinoindraw.githubyangke3.R
import com.rinoindraw.githubyangke3.databinding.ActivityFavoriteUserBinding
import com.rinoindraw.githubyangke3.main.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteUserBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: FavoriteUserAdapter

    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = getString(R.string.favorite_tittle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        favoriteUserViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteUserViewModel.getAllFavorites().observe(this, { favoriteList ->
            if (favoriteList != null) {
                adapter.setFavorites(favoriteList)
            }
        })
        adapter = FavoriteUserAdapter()
        binding?.rvFavorites?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorites?.setHasFixedSize(false)
        binding?.rvFavorites?.adapter = adapter
    }

    /**
     * Function to return View Model Factory
     */
    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}