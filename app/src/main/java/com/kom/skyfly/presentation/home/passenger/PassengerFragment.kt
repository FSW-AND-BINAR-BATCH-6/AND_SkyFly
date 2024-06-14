package com.kom.skyfly.presentation.home.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.databinding.FragmentPassengerBinding

class PassengerFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPassengerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPassengerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setOnclickListener()
    }

    private fun setOnclickListener() {
        binding.ivClosePassengerPage.setOnClickListener {
            dismiss()
        }
    }
}
