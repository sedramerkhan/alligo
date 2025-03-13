package com.example.e_commercenativexml.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.e_commercenativexml.R
import com.example.e_commercenativexml.databinding.FragmentMainContainerBinding
import com.example.e_commercenativexml.presentation.cart.CartViewModel
import kotlinx.coroutines.launch


class MainContainerFragment : Fragment() {

    private val cartViewModel: CartViewModel by activityViewModels()


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
        navbarListener()

        binding.mainContainerCart.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.main_activity_container)

            val action =
                MainContainerFragmentDirections.actionMainContainerFragmentToCartFragment()
            navController.navigate(action)
        }

        observeCartQuantity()

    }

    // Observe navigation changes and update title
    private fun navbarListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.mainContainerTitle.text = getString(
                when (destination.id) {
                    R.id.navigation_home -> {
                        R.string.title_home
                    }

                    else -> {
                        R.string.title_setting
                    }
                }
            )
        }
    }

    private fun setBottomNavbar() {
        // Set up the NavController with the bottom nav navigation graph
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment

        navController = navHostFragment.navController
        // Set up BottomNavigationView to work with the NavController
        binding.navView.setupWithNavController(navController)
    }

    private fun observeCartQuantity() {
        viewLifecycleOwner.lifecycleScope.launch {
            cartViewModel.cartsQuantityState.collect {

                val cartQuantityView = binding.mainContainerCartQuantity
                if (it > 0) {
                    //Update quantity
                    cartQuantityView.visibility = View.VISIBLE
                    cartQuantityView.text = it.toString()

                    //Shake cart icon
                    shakeAnimation(binding.mainContainerCartIcon)

                } else {
                    cartQuantityView.visibility = View.GONE

                }
            }
        }
    }

    private fun shakeAnimation(view: View) {
        val shake = ObjectAnimator.ofFloat(
            view,
            "translationX",
            0f,
            10f,
            -10f,
            10f,
            -10f,
            0f
        )
        shake.duration = 500
        shake.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Avoid memory leaks by setting the binding to null
        _binding = null
    }
}
