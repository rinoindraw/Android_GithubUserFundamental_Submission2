package com.rinoindraw.githubyangke3.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rinoindraw.githubyangke3.main.detail.DetailUserActivity
import com.rinoindraw.githubyangke3.GithubUser
import com.rinoindraw.githubyangke3.R
import com.rinoindraw.githubyangke3.databinding.ActivityMainBinding
import com.rinoindraw.githubyangke3.main.favorite.FavoriteUserActivity
import com.rinoindraw.githubyangke3.main.theme.SettingPreferences
import com.rinoindraw.githubyangke3.main.theme.ThemeSettingActivity
import com.rinoindraw.githubyangke3.main.theme.ThemeSettingViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, ThemeSettingViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)

            btnSearch.setOnClickListener{
                searchUser()
            }

            etQuery.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        mainViewModel.listGithubUser.observe(this) { listGithubUser ->
            setUserData(listGithubUser)
            showLoading(false)
        }

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


    }

    private fun searchUser(){
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            mainViewModel.setSearchUsers(query)
        }
    }

    private fun setUserData(listGithubUser: List<GithubUser>) {
        val listUser = ArrayList<GithubUser>()
        for (user in listGithubUser) {
            listUser.clear()
            listUser.addAll(listGithubUser)
        }
        val adapter = UserAdapter(listUser)

        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                showSelectedUser(data)
            }
        })


    }

    private fun showSelectedUser(data: GithubUser) {
        val moveWithParcelableIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
        moveWithParcelableIntent.putExtra(DetailUserActivity.EXTRA_USER, data)
        startActivity(moveWithParcelableIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean){
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favorites ->{
                val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.theme ->{
                val intent = Intent(this@MainActivity, ThemeSettingActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> true
        }
    }

}