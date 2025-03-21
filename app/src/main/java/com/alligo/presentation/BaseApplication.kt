package  com.alligo.presentation

import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.core.os.ConfigurationCompat
import com.alligo.data.local.preference.AppPreferences
import com.alligo.presentation.utils.AppSettingManager
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale


@HiltAndroidApp
class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        appPreferences = AppPreferences(applicationContext)

        // Apply the saved theme preference on app launch
        isDarkMode = appPreferences.theme == "dark"
        AppSettingManager.applyTheme(isDarkMode)


        Log.i("Setting", "Locale ${appPreferences.locale} theme ${appPreferences.theme}")

        val sysLocales = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
        Log.i(
            "Setting", "sys ${
                sysLocales[0]?.language
            }    ${Locale.getDefault().language}"
        )


    }


    companion object {
        lateinit var appPreferences: AppPreferences
        var isDarkMode = false
    }


}