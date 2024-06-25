package com.kom.skyfly.presentation.account.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentBottomSheetsChangePasswordBinding
import com.kom.skyfly.utils.proceedWhen
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetsChangePassword : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetsChangePasswordBinding
    private val sharedViewModelEditProfile: SharedViewModelEditProfile by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentBottomSheetsChangePasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnSubmit.setOnClickListener {
            if (isFormValid()) {
                confirmUpdateProfile()
            }
        }
    }

    private fun doChangePassword() {
        if (isFormValid()) {
            val newPassword = binding.etNewPassword.text.toString().trim()
            val confirmNewPassword = binding.etConfirmPassword.text.toString().trim()
            proceedChangePassword(newPassword, confirmNewPassword)
        }
    }

    private fun proceedChangePassword(
        newPassword: String,
        confirmNewPassword: String,
    ) {
        sharedViewModelEditProfile.updateProfile(null, null, newPassword, confirmNewPassword)
            .observe(viewLifecycleOwner) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        binding.btnSubmit.isVisible = true
                        binding.pbLoading.isVisible = false
                        Toasty.success(
                            requireContext(),
                            getString(R.string.text_change_password_successfully),
                            Toast.LENGTH_SHORT,
                        ).show()
                        dismiss()
                    },
                    doOnError = {
                        binding.btnSubmit.isVisible = true
                        binding.pbLoading.isVisible = false
                        Toasty.error(
                            requireContext(),
                            getString(R.string.text_something_went_wrong),
                            Toast.LENGTH_SHORT,
                        ).show()
                    },
                    doOnLoading = {
                        binding.btnSubmit.isEnabled = false
                        binding.pbLoading.isVisible = true
                    },
                )
            }
    }

    private fun confirmUpdateProfile() {
        val dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.text_confirm_change_password))
                .setPositiveButton(
                    getString(R.string.text_yes),
                ) { dialog, id ->
                    doChangePassword()
                }
                .setNegativeButton(
                    getString(R.string.text_no),
                ) { dialog, id ->
                }
                .create()
        dialog.show()
    }

    private fun passwordValidation(
        confirmPassword: String,
        textInputLayout: TextInputLayout,
    ): Boolean {
        return if (confirmPassword.isEmpty()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = getString(R.string.text_password_cannot_empty)
            false
        } else if (confirmPassword.length < 8) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = getString(R.string.text_password_should_be_8_character)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun passwordAndConfirmPasswordValidation(
        password: String,
        confirmPassword: String,
    ): Boolean {
        return if (password == confirmPassword) {
            binding.tilNewPassword.isErrorEnabled = false
            binding.tilConfirmPassword.isErrorEnabled = false
            true
        } else {
            binding.tilNewPassword.isErrorEnabled = true
            binding.tilNewPassword.error = getString(R.string.text_password_doesnt_match)
            binding.tilConfirmPassword.isErrorEnabled = true
            binding.tilConfirmPassword.error =
                getString(R.string.text_password_doesnt_match)
            false
        }
    }

    private fun isFormValid(): Boolean {
        val newPassword = binding.etNewPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        return passwordValidation(newPassword, binding.tilNewPassword) &&
            passwordValidation(confirmPassword, binding.tilConfirmPassword) &&
            passwordAndConfirmPasswordValidation(newPassword, confirmPassword)
    }
}
