package com.alligo.presentation.utils

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.alligo.databinding.ToastContainerBinding

object ToastUtils {


    fun show(activity: Activity, text: String) {
        val inflater = LayoutInflater.from(activity)
        val binding = ToastContainerBinding.inflate(inflater)

        binding.toastText.text = text

        Toast(activity.applicationContext).apply {
            setGravity(Gravity.FILL_HORIZONTAL or Gravity.TOP, 0, 0)
            duration = Toast.LENGTH_LONG
            view = binding.root
            show()
        }
    }


}