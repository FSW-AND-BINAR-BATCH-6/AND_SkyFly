package com.kom.skyfly.presentation.verifyotp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentVerifyOtpBinding
import com.kom.skyfly.presentation.login.LoginActivity
import com.kom.skyfly.utils.proceedWhen
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyOtpFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentVerifyOtpBinding
    private val verifyOtpViewModel: VerifyOtpViewModel by viewModel()
    private lateinit var token: String
    private lateinit var phoneNumber: String
    private var countdownTimer: CountDownTimer? = null

    companion object {
        private const val ARG_TOKEN = "arg_token"
        private const val ARG_EMAIL = "arg_email"
        private const val ARG_PHONE_NUMBER = "arg_phone_number"

        fun newInstance(
            token: String?,
            email: String?,
            phoneNumber: String?,
        ): VerifyOtpFragment {
            val fragment = VerifyOtpFragment()
            val args = Bundle()
            args.putString(ARG_TOKEN, token)
            args.putString(ARG_EMAIL, email)
            args.putString(ARG_PHONE_NUMBER, phoneNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVerifyOtpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        startCountdownTimer()
        setOnClickListeners()
        getOtpViewValue()
        val email = arguments?.getString(ARG_EMAIL).toString()
        phoneNumber = arguments?.getString(ARG_PHONE_NUMBER).toString()
        token = arguments?.getString(ARG_TOKEN).toString()
        binding.tvEmailValue.text = maskEmail(email)
    }

    private fun maskEmail(email: String): String {
        val atIndex = email.indexOf('@')
        if (atIndex <= 1) {
            return email
        }
        val maskedPart = "*".repeat(atIndex - 1)
        return email[0] + maskedPart + email.substring(atIndex - 3)
    }

    private fun setOnClickListeners() {
        binding.tvResendOtp.setOnClickListener {
            requestResendOtp(token)
            startCountdownTimer()
        }
        binding.tvReqNewOtp.setOnClickListener {
            requestResendOtpSms(token, phoneNumber)
            startCountdownTimer()
        }
    }

    private fun getOtpViewValue() {
        val otpView = binding.otpView
        otpView.setOtpCompletionListener { otp ->
            Log.d("Actual Value", otp)

            if (otp.length == otpView.itemCount) {
                verifyProceed(token, otp)
            }
        }
    }

    private fun verifyProceed(
        token: String?,
        otp: String,
    ) {
        if (token != null) {
            verifyOtpViewModel.verifyOtp(token, otp).observe(viewLifecycleOwner) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.text_success_verify_account),
                            Toast.LENGTH_SHORT,
                        ).show()
                        navigateToLogin()
                    },
                    doOnError = {
                        Toasty.error(requireContext(), "Invalid OTP!", Toast.LENGTH_SHORT).show()
                        binding.pbOtp.isVisible = false
                        Log.d("Verify-Error", "verifyProceed: ${it.exception?.message}")
                    },
                    doOnLoading = {
                        binding.tvReqNewOtp.isVisible = false
                        binding.pbOtp.isVisible = true
                    },
                )
            }
        }
    }

    private fun requestResendOtp(token: String) {
        verifyOtpViewModel.resendOtpRequest(token).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_otp_has_been_sent_to_your_email),
                        Toast.LENGTH_SHORT,
                    ).show()
                    it.payload?.let {
                        val newToken = it.token
                        if (newToken != null) {
                            this@VerifyOtpFragment.token = newToken
                        }
                    }
                },
                doOnError = {
                    Log.d("resendOtpError", "requestResendOtp: Error ${it.exception?.message}")
                },
            )
        }
    }

    private fun requestResendOtpSms(
        token: String,
        phoneNumber: String,
    ) {
        Log.d("VerifyOtpFragment", "Requesting resend OTP for token: $token")
        verifyOtpViewModel.resendOtpSmsRequest(token, phoneNumber)
            .observe(viewLifecycleOwner) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        Log.d("VerifyOtpFragment", "Resend OTP success: ${it.message}")
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.text_otp_has_been_sent_to_your_sms),
                            Toast.LENGTH_SHORT,
                        ).show()
                        it.payload?.let {
                            val newToken = it.token
                            if (newToken != null) {
                                this@VerifyOtpFragment.token = newToken
                            }
                        }
                    },
                    doOnError = {
                        Log.d("resendOtpError", "requestResendOtp: Error ${it.exception?.message}")
                    },
                )
            }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(requireActivity(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }

    private fun startCountdownTimer() {
        countdownTimer?.cancel()

        countdownTimer =
            object : CountDownTimer(60000, 1000) {
                @SuppressLint("StringFormatMatches")
                override fun onTick(millisUntilFinished: Long) {
                    if (isAdded) {
                        binding.tvResendOtpCoundown.text =
                            getString(R.string.text_countdown_otp, millisUntilFinished / 1000)
                    }
                }

                override fun onFinish() {
                    if (isAdded) {
                        binding.tvResendOtp.isVisible = true
                        binding.tvResendOtpCoundown.isVisible = false
                    }
                }
            }
        countdownTimer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countdownTimer?.cancel()
    }
}
