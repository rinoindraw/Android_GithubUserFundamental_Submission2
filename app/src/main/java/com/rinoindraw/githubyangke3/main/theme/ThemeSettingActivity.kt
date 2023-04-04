package com.rinoindraw.githubyangke3.main.theme

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.rinoindraw.githubyangke3.R
import com.rinoindraw.githubyangke3.databinding.ActivityThemeSettingBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class ThemeSettingActivity : AppCompatActivity() {

    private var _binding: ActivityThemeSettingBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityThemeSettingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Theme Setting"

        val switch = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)

        val viewModel = ViewModelProvider(this, ThemeSettingViewModelFactory(pref)).get(
            ThemeSettingViewModel::class.java
        )

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switch.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switch.isChecked = false
            }
        }

        switch.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}