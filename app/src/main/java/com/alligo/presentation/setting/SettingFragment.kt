package  com.alligo.presentation.setting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alligo.R

import com.alligo.databinding.FragmentSettingBinding
import com.alligo.presentation.BaseApplication
import com.alligo.presentation.MainActivity
import com.alligo.presentation.utils.AppSettingManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //Control Theme
        binding.themeSwitch.apply {
            isChecked = BaseApplication.isDarkMode
            setOnCheckedChangeListener { _, isChecked ->
                BaseApplication.isDarkMode = isChecked
                BaseApplication.appPreferences.theme = if (isChecked) "dark" else "light"
                AppSettingManager.applyTheme(isChecked)
            }
        }

        binding.radioGroupLanguage.setOnCheckedChangeListener(null)

        //Control Locale
        binding.radioGroupLanguage.check(
            if (BaseApplication.appPreferences.locale=="ar") R.id.radio_arabic
            else R.id.radio_english
        )

        binding.radioGroupLanguage.setOnCheckedChangeListener { _, checkedId ->
            val newLocale = if (checkedId == R.id.radio_english) "en" else "ar"
            Log.i("setting", "language clicked $newLocale")
            BaseApplication.appPreferences.locale = newLocale


            (activity as? Activity)?.recreate()

            // Restart activity properly
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context?.startActivity(intent)
        }

        //Logout
        binding.logout.setOnClickListener {

        }
        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}