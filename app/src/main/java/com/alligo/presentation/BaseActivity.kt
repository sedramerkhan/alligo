package  com.alligo.presentation


import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.alligo.presentation.utils.AppSettingManager
import com.alligo.presentation.utils.KeyboardUtils
import java.util.Locale

open class BaseActivity : AppCompatActivity() {


    override fun attachBaseContext(newBase: Context?) {
        newBase?.let { ctx ->
            // Be sure that prefs are initialized
            BaseApplication.appPreferences.locale.run {
                val locale = Locale.forLanguageTag(this)
                val localeUpdatedContext = AppSettingManager.applyLocale(ctx, locale)
                super.attachBaseContext(localeUpdatedContext)
                return
            }
        }
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        KeyboardUtils.hide(this)
//        return super.onTouchEvent(event)
//    }


}