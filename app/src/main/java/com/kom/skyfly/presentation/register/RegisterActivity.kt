package com.kom.skyfly.presentation.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityRegisterBinding
import com.kom.skyfly.presentation.login.LoginActivity
import com.kom.skyfly.presentation.verifyotp.VerifyOtpFragment
import com.kom.skyfly.utils.highLightWord

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRegisterForm()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.tvNotHaveAccount.highLightWord(getString(R.string.text_login)) {
            navigateToLogin()
        }
        binding.btnRegister.setOnClickListener {
            openOtpModal()
        }
    }

    private fun openOtpModal() {
        val verifyOtpFragment = VerifyOtpFragment()
        verifyOtpFragment.show(supportFragmentManager, verifyOtpFragment.tag)
    }

    private fun setRegisterForm() {
        with(binding.layoutForm) {
            tilFullName.isVisible = true
            tilEmail.isVisible = true
            tilNoTlp.isVisible = true
            tilPassword.isVisible = true
            tilConfirmPassword.isVisible = true
        }
    }

    private fun isFormValid(): Boolean {
        val fullName = binding.layoutForm.etFullName.text.toString().trim()
        val email = binding.layoutForm.etEmail.text.toString().trim()
        val phoneNumber = binding.layoutForm.etNoTlp.toString().trim()
        val password = binding.layoutForm.etPassword.text.toString().trim()
        val confirmPassword = binding.layoutForm.etConfirmPassword.text.toString().trim()

        return fullNameValidation(fullName) &&
            emailValidation(email) &&
            phoneNumberValidation(phoneNumber) &&
            passwordValidation(password, binding.layoutForm.tilPassword) &&
            passwordValidation(confirmPassword, binding.layoutForm.tilConfirmPassword) &&
            passwordAndConfirmPasswordValidation(password, confirmPassword)
    }

    private fun fullNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.layoutForm.tilFullName.isErrorEnabled = true
            binding.layoutForm.tilFullName.error = getString(R.string.text_fullname_cannot_empty)
            false
        } else {
            binding.layoutForm.tilFullName.isErrorEnabled = false
            true
        }
    }

    private fun phoneNumberValidation(phoneNumber: String): Boolean {
        return if (phoneNumber.isEmpty()) {
            binding.layoutForm.tilNoTlp.isErrorEnabled = true
            binding.layoutForm.tilNoTlp.error = getString(R.string.text_no_tlp_cannot_empty)
            false
        } else {
            binding.layoutForm.tilNoTlp.isErrorEnabled = false
            true
        }
    }

    private fun emailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.error = getString(R.string.text_email_cannot_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.error = getString(R.string.text_invalid_email_format)
            false
        } else {
            binding.layoutForm.tilEmail.isErrorEnabled = false
            true
        }
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
            binding.layoutForm.tilPassword.isErrorEnabled = false
            binding.layoutForm.tilConfirmPassword.isErrorEnabled = false
            true
        } else {
            binding.layoutForm.tilPassword.isErrorEnabled = true
            binding.layoutForm.tilPassword.error = getString(R.string.text_password_doesnt_match)
            binding.layoutForm.tilConfirmPassword.isErrorEnabled = true
            binding.layoutForm.tilConfirmPassword.error =
                getString(R.string.text_password_doesnt_match)
            false
        }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }
}
