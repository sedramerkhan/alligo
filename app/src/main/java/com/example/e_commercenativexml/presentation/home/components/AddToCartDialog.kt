package com.example.e_commercenativexml.presentation.home.components

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.e_commercenativexml.databinding.DialogAddToCartBinding
import com.example.e_commercenativexml.model.product.Product
import com.example.e_commercenativexml.presentation.AddToCartViewModel
import com.example.e_commercenativexml.presentation.utils.ImageService
import com.example.e_commercenativexml.presentation.utils.extentions.formatPrice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddToCartDialog(
    private val product: Product,

    ) : DialogFragment() {

    private val viewModel: AddToCartViewModel by viewModels()

    private var _binding: DialogAddToCartBinding? = null
    private val binding get() = _binding!!


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())


        // Inflate the binding
        _binding = DialogAddToCartBinding.inflate(layoutInflater)

        // Set up the view
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Bind data to views
        binding.dialogCartTitle.text = product.title
        binding.dialogCartPrice.text = product.price.formatPrice()


        ImageService.setImage(
            binding.dialogCartImage.image,
            binding.dialogCartImage.imageShimmer,
            product.thumbnail
        ) // Adjust based on your `custom_image_view.xml`


        setOnClickListeners()
        //  Initialize ViewModel
        viewModel.setProduct(product)



        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.itemQuantityState.collect {
                binding.dialogCartCounter.counterQuantity.text = it.toString()
            }
        }
        return binding.root
    }

    private fun setOnClickListeners() {
        viewModel.run {
            binding.dialogCartCounter.counterBtnDecrease.setOnClickListener {
                decreaseQuantity()
            }

            binding.dialogCartCounter.counterBtnIncrease.setOnClickListener {
                Log.i("prodcut", "increase")

                increaseQuantity()
            }

            binding.dialogCartAddToCart.setOnClickListener {
                addToCart()
                dismiss()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
