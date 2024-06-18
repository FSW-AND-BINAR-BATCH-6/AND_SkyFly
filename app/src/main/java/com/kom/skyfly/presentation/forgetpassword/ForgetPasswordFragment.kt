package com.kom.skyfly.presentation.forgetpassword

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentResetPasswordBinding
import com.kom.skyfly.utils.proceedWhen
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgetPasswordFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentResetPasswordBinding
    private val forgetPasswordViewModel: ForgetPasswordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentResetPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupForm()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnReset.setOnClickListener {
            requestResetPassword()
        }
    }

    private fun requestResetPassword() {
        if (isFormValid()) {
            val email = binding.etEmail.text.toString().trim()
            proceedReqForgetPassword(email)
        }
    }

    private fun proceedReqForgetPassword(email: String) {
        forgetPasswordViewModel.forgetPassword(email).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.btnReset.isVisible = true
                    binding.pbLoading.isVisible = false
                    Toasty.success(
                        requireContext(),
                        getString(R.string.text_request_change_password_has_been_sent_to_your_email),
                        Toast.LENGTH_SHORT,
                    ).show()
                    dismiss()
                },
                doOnError = {
                    binding.btnReset.isVisible = true
                    binding.pbLoading.isVisible = false
                    Log.d("Reset-Error", "proceedReqForgetPassword: ${it.exception?.message} ")
                },
                doOnLoading = {
                    binding.btnReset.isVisible = false
                    binding.pbLoading.isVisible = true
                },
            )
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        return emailValidation(email)
    }

    private fun emailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_email_cannot_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_invalid_email_format)
            false
        } else {
            binding.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun setupForm() {
        with(binding) {
            tilEmail.isVisible = true
            btnReset.isVisible = true
        }
    }
}
