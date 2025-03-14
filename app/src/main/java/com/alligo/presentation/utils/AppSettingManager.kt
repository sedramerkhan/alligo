package  com.alligo.presentation.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.ConfigurationCompat
import java.util.Locale

object AppSettingManager {
    fun applyTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun applyLocale(context: Context, locale: Locale): ContextWrapper {
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration

        // Update system locale
        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)

        // Update context with new configuration
        val updatedContext = context.createConfigurationContext(configuration)
        return ContextWrapper(updatedContext)
    }

     fun getSystemDefaultLocale(): String {
        val sysLocales = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
        return sysLocales[0]?.language ?: Locale.getDefault().language ?: "en"
    }

    fun getSystemDefaultTheme(): String {
        return if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            "dark"
        else
            "light"
    }

}