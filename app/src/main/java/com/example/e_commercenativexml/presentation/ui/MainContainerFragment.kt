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

        replaceFragment(HomeFragment())

        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_dashboard -> replaceFragment(NotificationsFragment())
                R.id.navigation_notifications -> replaceFragment(DashboardFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frameLayout, fragment)?.commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Avoid memory leaks by setting the binding to null
        _binding = null
    }
}
