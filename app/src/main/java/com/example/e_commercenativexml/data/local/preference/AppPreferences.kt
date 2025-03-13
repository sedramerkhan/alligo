package com.example.e_commercenativexml.data.local.preference

import android.content.Context
import androidx.core.content.edit


class AppPreferences(context: Context) {
    private val mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    var locale
        get() = mPrefs.getString("locale_pref", "en")
        set(value) = mPrefs.edit { putString("locale_pref", value) }

    var theme
        get() = mPrefs.getString("theme_pref", "light")
        set(value) = mPrefs.edit { putString("theme_pref", value) }
}

