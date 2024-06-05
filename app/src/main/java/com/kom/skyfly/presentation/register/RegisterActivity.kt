package com.kom.skyfly.presentation.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityRegisterBinding
import com.kom.skyfly.presentation.login.LoginActivity
import com.kom.skyfly.presentation.verifyotp.VerifyOtpFragment
import com.kom.skyfly.utils.highLightWord
import com.kom.skyfly.utils.proceedWhen
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupEmailValidation()
        setRegisterForm()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.tvNotHaveAccount.highLightWord(getString(R.string.text_login)) {
            navigateToLogin()
        }
        binding.btnRegister.setOnClickListener {
            doRegister()
        }
    }

    private fun openOtpModal(
        token: String?,
        email: String?,
    ) {
        val verifyOtpFragment = VerifyOtpFragment.newInstance(token, email)
        verifyOtpFragment.show(supportFragmentManager, verifyOtpFragment.tag)
    }

    private fun doRegister() {
        if (isFormValid()) {
            val email = binding.layoutForm.etEmail.text.toString().trim()
            val password = binding.layoutForm.etPassword.text.toString().trim()
            val fullName = binding.layoutForm.etFullName.text.toString().trim()
            val phoneNumber = binding.layoutForm.etNoTlp.text.toString().trim()
            proceedRegister(fullName, email, phoneNumber, password)
        }
    }

    private fun proceedRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ) {
        registerViewModel.doRegister(fullName, email, phoneNumber, password)
            .observe(this) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        binding.pbLoading.isVisible = false
                        binding.btnRegister.isVisible = true
                        result.payload?.let {
                            val token = it.token
                            openOtpModal(token, email)
                        }
                    },
                    doOnError = {
                        binding.pbLoading.isVisible = false
                        binding.btnRegister.isVisible = true
                        Toasty.error(
                            this,
                            getString(
                                R.string.text_register_failed,
                                it.exception?.message.orEmpty(),
                            ),
                            Toast.LENGTH_SHORT,
                            true,
                        ).show()
                    },
                    doOnLoading = {
                        binding.pbLoading.isVisible = true
                        binding.btnRegister.isVisible = false
                    },
                )
            }
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
            binding.layoutForm.tilEmail.endIconMode = TextInputLayout.END_ICON_NONE
            binding.layoutForm.tilEmail.error = getString(R.string.text_email_cannot_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.endIconMode = TextInputLayout.END_ICON_NONE
            binding.layoutForm.tilEmail.error = getString(R.string.text_invalid_email_format)
            false
        } else {
            binding.layoutForm.tilEmail.isErrorEnabled = false
            binding.layoutForm.tilEmail.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
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

    private fun setupEmailValidation() {
        binding.layoutForm.etEmail.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    emailValidation(s.toString())
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            },
        )
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }
}
