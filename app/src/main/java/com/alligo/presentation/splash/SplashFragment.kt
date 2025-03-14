package com.alligo.presentation.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.alligo.R
import com.alligo.presentation.BaseApplication
import com.alligo.presentation.MainActivity

/**
to determine destination
 */
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        decideNavigation()
    }


    private fun decideNavigation() {
        val token = BaseApplication.appPreferences.token // Get token from shared preferences
        Log.i("splash", token)


        if (token.isNotEmpty()) {
            findNavController().navigate(R.id.action_splashFragment_to_mainContainerFragment,
                null,
                navOptions {
                    popUpTo(R.id.splashFragment) { inclusive = true }
                })
        } else {
            findNavController().navigate(
                R.id.action_splashFragment_to_loginFragment,
                null,
                navOptions {
                    popUpTo(R.id.splashFragment) { inclusive = true }
                })
        }


        (activity as MainActivity).isReady = true // Allow splash screen to be removed
    }
}