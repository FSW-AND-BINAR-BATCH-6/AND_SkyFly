package com.kom.skyfly.presentation.verifyotp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.databinding.FragmentVerifyOtpBinding

class VerifyOtpFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentVerifyOtpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVerifyOtpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
