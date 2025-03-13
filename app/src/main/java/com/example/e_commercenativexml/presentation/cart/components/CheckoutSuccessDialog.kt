package com.example.e_commercenativexml.presentation.cart.components

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.e_commercenativexml.databinding.DialogCheckoutSuccessBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckoutSuccessDialog(
    private val onClick: () -> Unit,
) : DialogFragment() {


    private var _binding: DialogCheckoutSuccessBinding? = null
    private val binding get() = _binding!!


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())


        // Inflate the binding
        _binding = DialogCheckoutSuccessBinding.inflate(layoutInflater)

        // Set up the view
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



        binding.dialogCheckoutSuccessBtn.setOnClickListener {
            dismiss()
            findNavController().navigateUp()
            onClick()
        }




        return dialog
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
