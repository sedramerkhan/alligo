package com.example.e_commercenativexml.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.e_commercenativexml.R
import com.example.e_commercenativexml.databinding.FragmentMainContainerBinding


class MainContainerFragment : Fragment() {

    private var _binding: FragmentMainContainerBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      setBottomNavbar()

        binding.mainContainerCartItem.setOnClickListener {
            Log.i("Hi","gffgf")
        }
    }


    private fun setBottomNavbar(){
        // Set up the NavController with the bottom nav navigation graph
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment

        navController = navHostFragment.navController
        // Set up BottomNavigationView to work with the NavController
        binding.navView.setupWithNavController(navController)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Avoid memory leaks by setting the binding to null
        _binding = null
    }
}
