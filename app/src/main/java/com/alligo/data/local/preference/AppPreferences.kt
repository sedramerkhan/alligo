package  com.alligo.data.local.preference

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.os.ConfigurationCompat
import java.util.Locale


class AppPreferences(context: Context) {
    private val mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    var locale: String
        get() = mPrefs.getString("locale_pref", getSystemDefaultLocale()) ?: "en"
        set(value) = mPrefs.edit { putString("locale_pref", value) }

    private fun getSystemDefaultLocale(): String {
        val sysLocales = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
        return sysLocales[0]?.language ?: Locale.getDefault().language ?: "en"
    }

    var theme: String
        get() = mPrefs.getString("theme_pref", getSystemDefaultTheme()) ?: "light"
        set(value) = mPrefs.edit { putString("theme_pref", value) }

    private fun getSystemDefaultTheme(): String {
        return if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            "dark"
        else
            "light"
    }

}

