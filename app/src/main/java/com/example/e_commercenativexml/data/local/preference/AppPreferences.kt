package com.example.e_commercenativexml.data.local.preference

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.os.ConfigurationCompat
import java.util.Locale


class AppPreferences(context: Context) {
    private val mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    var locale: String
        get() {
            var lang = mPrefs.getString("locale_pref", null)
            if (lang == null) {
                val sysLocales = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
                lang = sysLocales[0]?.language
                if (lang == null)
                    lang = Locale.getDefault().language

                mPrefs.edit { putString("locale_pref", lang) }
            }
            return lang!!
        }

        set(value) = mPrefs.edit { putString("locale_pref", value) }

    var theme
        get() :String {
            var theme = mPrefs.getString("theme_pref", null)
            theme?.let {
                val currentMode = AppCompatDelegate.getDefaultNightMode()
                theme = if (currentMode == AppCompatDelegate.MODE_NIGHT_YES)
                    "dark"
                else
                    "light"
                mPrefs.edit { putString("theme_pref", theme) }
            }
            return theme!!
        }

        set(value) = mPrefs.edit { putString("theme_pref", value) }
}

