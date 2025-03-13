package com.example.e_commercenativexml.presentation.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercenativexml.databinding.FragmentCartBinding
import com.example.e_commercenativexml.model.CartItem
import com.example.e_commercenativexml.presentation.cart.components.CartAdapter
import com.example.e_commercenativexml.presentation.cart.components.SwipeHandler
import com.example.e_commercenativexml.presentation.utils.extentions.formatPrice
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

        recyclerViewInitializer()
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cartItemsState.collect { items ->
                setData(items)
            }
        }

    }


    private fun setData(items: List<CartItem>) {
        cartAdapter.bindData(items)

        val originalPrice = viewModel.originalPrice
        binding.cartOriginalPriceValue.text = originalPrice.formatPrice()


        val discount = viewModel.discount
        binding.cartDiscountValue.text = discount.formatPrice()

        binding.cartTotalValue.text = (originalPrice - discount).formatPrice()
    }

    private fun recyclerViewInitializer() {

        cartAdapter = CartAdapter(
            onItemDecreased = {
                viewModel.decreaseItemQuantity(it)
            },
            onItemIncreased = {
                viewModel.increaseItemQuantity(it)
            }
        )
        binding.cartRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }

        val itemTouchHelper = ItemTouchHelper(
            SwipeHandler.simpleItemTouchCallback(
                context = requireContext(),
                onDelete = {
                    viewModel.deleteItem(it)
                },

                )
        )

        itemTouchHelper.attachToRecyclerView(binding.cartRecyclerview)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}