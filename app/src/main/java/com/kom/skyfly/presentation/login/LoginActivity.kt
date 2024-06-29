package com.kom.skyfly.presentation.login

import android.annotation.SuppressLint
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
import com.kom.skyfly.databinding.ActivityLoginBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.presentation.forgetpassword.ForgetPasswordFragment
import com.kom.skyfly.presentation.main.MainActivity
import com.kom.skyfly.presentation.register.RegisterActivity
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.highLightWord
import com.kom.skyfly.utils.proceedWhen
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setLoginForm()
        setupEmailValidation()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.tvNotHaveAccount.highLightWord(getString(R.string.text_register)) {
            navigateToRegister()
        }
        binding.tvForgetPassword.setOnClickListener {
            showResetPasswordForm()
        }
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun setLoginForm() {
        with(binding) {
            layoutForm.tilEmail.isVisible = true
            layoutForm.tilPassword.isVisible = true
        }
    }

    private fun doLogin() {
        if (isFormValid()) {
            val email = binding.layoutForm.etEmail.text.toString().trim()
            val password = binding.layoutForm.etPassword.text.toString().trim()

            proceedLogin(email, password)
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun proceedLogin(
        email: String,
        password: String,
    ) {
        loginViewModel.doLogin(email, password).observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isEnabled = true
                    it.payload?.let { payload ->
                        val token = payload.token
                        loginViewModel.saveUserToken(token)
                    }

                    navigateAction()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    if (it.exception is NoInternetException) {
                        binding.csvLogin.setState(
                            ContentState.ERROR_NETWORK_GENERAL,
                            "Tidak ada internet!",
                        )
                    } else {
                        Toasty.error(
                            this@LoginActivity,
                            getString(R.string.email_or_password_is_incorrect),
                            Toast.LENGTH_SHORT,
                            true,
                        ).show()
                    }
                    binding.btnLogin.isEnabled = true
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogin.isEnabled = false
                },
            )
        }
    }

    private fun navigateAction() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }

    private fun navigateToRegister() {
        startActivity(
            Intent(this, RegisterActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }

    private fun showResetPasswordForm() {
        val resetPassword = ForgetPasswordFragment()
        resetPassword.show(supportFragmentManager, resetPassword.tag)
    }

    private fun isFormValid(): Boolean {
        val email = binding.layoutForm.etEmail.text.toString().trim()
        val password = binding.layoutForm.etPassword.text.toString().trim()

        return emailValidation(email) &&
            passwordValidation(
                password, binding.layoutForm.tilPassword,
            )
    }

    private fun emailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
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
}
