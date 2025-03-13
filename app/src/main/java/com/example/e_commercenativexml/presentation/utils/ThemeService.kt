package com.example.e_commercenativexml.presentation.utils

import androidx.appcompat.app.AppCompatDelegate

object ThemeService {
     fun applyDarkMode(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}