package com.example.e_commercenativexml.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.e_commercenativexml.R
import com.example.e_commercenativexml.databinding.ActivityMainBinding
import com.example.e_commercenativexml.databinding.FragmentMainContainerBinding
import com.example.e_commercenativexml.presentation.ui.dashboard.DashboardFragment
import com.example.e_commercenativexml.presentation.ui.home.HomeFragment
import com.example.e_commercenativexml.presentation.ui.notifications.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainContainerFragment : Fragment() {

    // Declare a binding variable
    private var _binding: FragmentMainContainerBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the binding layout
        _binding = FragmentMainContainerBinding.inflate(inflater, container, false)
        return binding.root // Return the root view from the binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the NavController with the main navigation graph
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment?
        // Check if the navHostFragment is null, if so, log an error
        if (navHostFragment != null) {
            navController = navHostFragment.navController

            // Set up BottomNavigationView to work with the NavController
            binding.navView.setupWithNavController(navController)
        } else {
            // Handle the case where the NavHostFragment is not found
            Log.e("MainContainerFragment", "NavHostFragment not found!")
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        // Avoid memory leaks by setting the binding to null
        _binding = null
    }
}
