package com.rinoindraw.githubyangke3.main.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.rinoindraw.githubyangke3.R
import com.rinoindraw.githubyangke3.databinding.ActivitySplashScreenBinding
import com.rinoindraw.githubyangke3.main.MainActivity
import com.rinoindraw.githubyangke3.main.theme.SettingPreferences
import com.rinoindraw.githubyangke3.main.theme.ThemeSettingViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, ThemeSettingViewModelFactory(pref)).get(
            SplashScreenViewModel::class.java
        )

        val imageLogo = findViewById<ImageView>(R.id.logo_titlebar)
        imageLogo.alpha = 0f
        imageLogo.animate().setDuration(1500).alpha(1f).withEndAction {
            val intentSplash = Intent(this, MainActivity::class.java)
            startActivity(intentSplash)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        viewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }
}