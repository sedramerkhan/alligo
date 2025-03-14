package  com.alligo.data.local.preference

import android.content.Context
import androidx.core.content.edit
import com.alligo.presentation.utils.AppSettingManager


class AppPreferences(context: Context) {
    private val mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    var locale: String
        get() = mPrefs.getString("locale_pref", AppSettingManager.getSystemDefaultLocale()) ?: "en"
        set(value) = mPrefs.edit { putString("locale_pref", value) }


    var theme: String
        get() = mPrefs.getString("theme_pref", AppSettingManager.getSystemDefaultTheme()) ?: "light"
        set(value) = mPrefs.edit { putString("theme_pref", value) }


    var token: String
        get() = mPrefs.getString("token_pref", "") ?: ""
        set(value) = mPrefs.edit { putString("token_pref", value) }

}

