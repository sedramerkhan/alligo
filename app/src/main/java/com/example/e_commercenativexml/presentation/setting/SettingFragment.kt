package com.example.e_commercenativexml.presentation.setting

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_commercenativexml.R

import com.example.e_commercenativexml.databinding.FragmentSettingBinding
import com.example.e_commercenativexml.presentation.BaseApplication
import com.example.e_commercenativexml.presentation.utils.AppSettingManager
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

        //Control Locale
        binding.radioGroupLanguage.check(
            if (BaseApplication.isArabic) R.id.radio_arabic
            else R.id.radio_english
        )
        // Set a listener for the RadioGroup to handle the selection
        binding.radioGroupLanguage.setOnCheckedChangeListener { _, checkedId ->
            val locale = if (checkedId == R.id.radio_english) "en"
            else "ar"
            BaseApplication.appPreferences.locale = locale

            (context as? Activity)?.recreate()
        }
        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}