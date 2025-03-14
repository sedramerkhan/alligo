package com.alligo.presentation.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alligo.databinding.FragmentLoginBinding
import com.alligo.presentation.utils.extentions.isValidEmail
import com.alligo.presentation.utils.extentions.isValidPassword

class LoginFragment : Fragment() {


    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!


    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root


       //  Button click listener
        binding.loginBtn.setOnClickListener {
                signIn()
        }

         return root

    }

    private fun signIn() {
        val email = binding.longinEditTextEmail.text.toString().trim()
        val password = binding.loginEditTextPasswordText.text.toString().trim()

        // Validate inputs
        val emailError = email.isValidEmail()
        val passwordError = password.isValidPassword()

        // Check for validation errors
        if (emailError != null) {
            binding.longinEditTextEmailLayout.error = emailError
        } else {
            binding.longinEditTextEmailLayout.error = null
        }

        if (passwordError != null) {
            binding.loginEditTextPasswordLayout.error = passwordError

        } else {
            binding.loginEditTextPasswordLayout.error = null
        }

        if (emailError != null && passwordError != null) {
        // Disable fields while sending API request
            //  disableFields()

            // Call your API for sign-in
        }
    }


    private fun disableFields() {
        binding.longinEditTextEmail.isEnabled = false
        binding.loginEditTextPasswordText.isEnabled = false
        binding.loginBtn.isEnabled = false
    }

    private fun enableFields() {
        binding.longinEditTextEmail.isEnabled = true
        binding.loginEditTextPasswordText.isEnabled = true
        binding.loginBtn.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}