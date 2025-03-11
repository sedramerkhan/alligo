package com.example.e_commercenativexml.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.e_commercenativexml.R
import com.example.e_commercenativexml.data.utils.NetworkResult
import com.example.e_commercenativexml.databinding.FragmentHomeBinding
import com.example.e_commercenativexml.presentation.MainContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.productsState.collect { state ->
                when (state) {
                    is NetworkResult.Loading -> {

                        binding.textHome.text = "Loading"
                    }

                    is NetworkResult.Success -> {
                        binding.textHome.text = state.data.products.size.toString()

                    }

                    is NetworkResult.Failure -> {
                        binding.textHome.text = state.message

                    }
                }
            }
        }

        binding.buttonHome.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.main_activity_container)

            val action =
                MainContainerFragmentDirections.actionMainContainerFragmentToProductDetailsFragment()
            navController.navigate(action)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}