package com.example.e_commercenativexml.presentation

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.e_commercenativexml.data.local.preference.AppPreferences
import com.example.e_commercenativexml.presentation.utils.ThemeService
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        appPreferences = AppPreferences(applicationContext)

        // Apply the saved theme preference on app launch
        isDarkMode = appPreferences.theme == "dark"
        ThemeService.applyDarkMode(isDarkMode)
    }



    companion object {
        lateinit var appPreferences: AppPreferences
        var isDarkMode = false
    }


}