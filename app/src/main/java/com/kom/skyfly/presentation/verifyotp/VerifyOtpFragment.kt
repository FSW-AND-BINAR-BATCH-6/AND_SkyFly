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
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyOtpFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentVerifyOtpBinding

    private val verifyOtpViewModel: VerifyOtpViewModel by viewModel()

    companion object {
        private const val ARG_TOKEN = "arg_token"

        fun newInstance(token: String?): VerifyOtpFragment {
            val fragment = VerifyOtpFragment()
            val args = Bundle()
            args.putString(ARG_TOKEN, token)
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
        getOtpViewValue()
    }

    private fun getOtpViewValue() {
        val otpView = binding.otpView
        otpView.setOtpCompletionListener { otp ->
            Log.d("Actual Value", otp)
            val token = arguments?.getString(ARG_TOKEN)
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
                        Log.d("Verify-Error", "verifyProceed: ${it.exception?.message}")
                    },
                )
            }
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
        val countdownTimer =
            object : CountDownTimer(60000, 1000) {
                @SuppressLint("StringFormatMatches")
                override fun onTick(millisUntilFinished: Long) {
                    binding.tvResendOtpCoundown.text =
                        getString(R.string.text_countdown_otp, millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    binding.tvResendOtp.isVisible = true
                    binding.tvResendOtpCoundown.isVisible = false
                    binding.tvResendOtp.setOnClickListener {
                        Toast.makeText(
                            requireContext(),
                            "OTP dikirim ke email anda!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        countdownTimer.start()
    }
}
