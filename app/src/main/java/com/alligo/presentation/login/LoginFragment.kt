package com.alligo.presentation.login


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.alligo.R
import com.alligo.data.utils.NetworkResult
import com.alligo.databinding.FragmentLoginBinding
import com.alligo.presentation.utils.KeyboardUtils
import com.alligo.presentation.utils.ToastUtils
import com.alligo.presentation.utils.extentions.isValidPassword
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root


        observeLogin()
        //  Button click listener
        binding.loginBtn.setOnClickListener {
            signIn()
        }

        binding.loginCard.setOnClickListener {
            this.activity?.let { KeyboardUtils.hide(it) }

            binding.loginCard.clearFocus()
        }



        return root

    }


    private fun observeLogin() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginState.collectLatest { state ->
                when (state) {

                    is NetworkResult.Loading -> {
                        // Show loading state (e.g., ProgressBar)
                        disableFields()
                    }

                    is NetworkResult.Success -> {
                        enableFields()
                        Log.i("Login", "success ${state.data}")

                        activity?.let { ToastUtils.show(it,
                            getString(R.string.logged_in_successfully)) }

                        findNavController().navigate(
                            R.id.action_loginFragment_to_mainContainerFragment,
                            null,
                            navOptions {
                                popUpTo(R.id.loginFragment) { inclusive = true }
                            })
                    }

                    is NetworkResult.Failure -> {
                        enableFields()
                        activity?.let { ToastUtils.show(it, getString(R.string.invalid_data)) }
                    }
                    else-> {}
                }
            }
        }

    }

    private fun signIn() {

        Log.i("login", "sign in")

        val username = binding.longinEditTextUsername.text.toString().trim()
        val password = binding.loginEditTextPassword.text.toString().trim()

        // Validate inputs
        val usernameError = if (username.isEmpty()) "Field is required" else null
        val passwordError = password.isValidPassword()

        // Check for validation errors
        if (usernameError != null) {
            binding.longinEditTextUsernameLayout.error = usernameError
        } else {
            binding.longinEditTextUsernameLayout.error = null
        }

        if (passwordError != null) {
            binding.loginEditTextPasswordLayout.error = passwordError

        } else {
            binding.loginEditTextPasswordLayout.error = null
        }
        Log.i("login", "$usernameError $passwordError  $username  $password")

        if (usernameError == null && passwordError == null) {
            // Disable fields while sending API request
            //  disableFields()

            // Call your API for sign-in
            viewModel.login(username, password)
        }
    }


    private fun disableFields() {
        binding.longinEditTextUsername.isEnabled = false
        binding.loginEditTextPassword.isEnabled = false
        binding.loginBtn.isEnabled = false
    }

    private fun enableFields() {
        binding.longinEditTextUsername.isEnabled = true
        binding.loginEditTextPassword.isEnabled = true
        binding.loginBtn.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}