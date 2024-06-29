package com.kom.skyfly.presentation.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityRegisterBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.presentation.login.LoginActivity
import com.kom.skyfly.presentation.verifyotp.VerifyOtpFragment
import com.kom.skyfly.utils.NoInternetException
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
        setupPhoneNumberValidation()
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
        phoneNumber: String?,
    ) {
        val verifyOtpFragment = VerifyOtpFragment.newInstance(token, email, phoneNumber)
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

    @SuppressLint("StringFormatInvalid")
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
                        binding.btnRegister.isEnabled = true
                        result.payload?.let {
                            val token = it.token
                            openOtpModal(token, email, phoneNumber)
                        }
                    },
                    doOnError = {
                        binding.pbLoading.isVisible = false
                        binding.btnRegister.isEnabled = true
                        if (it.exception is NoInternetException) {
                            binding.csvRegister.setState(
                                ContentState.ERROR_NETWORK_GENERAL,
                                "No Internet Connection!",
                            )
                        } else {
                            Toasty.error(
                                this,
                                getString(
                                    R.string.text_register_failed,
                                ),
                                Toast.LENGTH_SHORT,
                                true,
                            ).show()
                        }
                    },
                    doOnLoading = {
                        binding.pbLoading.isVisible = true
                        binding.btnRegister.isEnabled = false
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
            etNoTlp.setText(getString(R.string.default_phone_number_value))
        }
    }

    private fun isFormValid(): Boolean {
        val fullName = binding.layoutForm.etFullName.text.toString().trim()
        val email = binding.layoutForm.etEmail.text.toString().trim()
        val phoneNumber = binding.layoutForm.etNoTlp.text.toString().trim()
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
        Log.d("PhoneValidation", "Phone number entered: '$phoneNumber'")

        return if (phoneNumber.isEmpty()) {
            binding.layoutForm.tilNoTlp.isErrorEnabled = true
            binding.layoutForm.tilNoTlp.endIconMode = TextInputLayout.END_ICON_NONE
            binding.layoutForm.tilNoTlp.error = getString(R.string.text_no_tlp_cannot_empty)
            false
        } else if (!phoneNumber.startsWith("62")) {
            binding.layoutForm.tilNoTlp.isErrorEnabled = true
            binding.layoutForm.tilNoTlp.endIconMode = TextInputLayout.END_ICON_NONE
            binding.layoutForm.tilNoTlp.error = getString(R.string.text_no_tlp_must_start_with_62)
            false
        } else if (phoneNumber.length < 11 || phoneNumber.length > 13) {
            binding.layoutForm.tilNoTlp.isErrorEnabled = true
            binding.layoutForm.tilNoTlp.error = "Phone number must be >11 and <13 digits"
            false
        } else {
            binding.layoutForm.tilNoTlp.isErrorEnabled = false
            binding.layoutForm.tilNoTlp.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
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

    private fun setupPhoneNumberValidation() {
        binding.layoutForm.etNoTlp.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    phoneNumberValidation(s.toString())
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
