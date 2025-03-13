package com.example.e_commercenativexml.presentation.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercenativexml.data.utils.NetworkResult
import com.example.e_commercenativexml.databinding.FragmentCartBinding
import com.example.e_commercenativexml.presentation.cart.components.CartAdapter
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private val viewModel: CartViewModel by activityViewModels()

    private lateinit var cartAdapter: CartAdapter


    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        cartAdapter = CartAdapter(
            onItemDecreased = {
                viewModel.decreaseQuantity(it)
            },
            onItemIncreased = {
                viewModel.increaseQuantity(it)
            }
        )
        binding.cartRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cartItemsState.collect { items ->
                cartAdapter.bindData(items)
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}