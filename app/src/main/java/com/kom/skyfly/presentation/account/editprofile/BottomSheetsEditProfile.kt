package com.kom.skyfly.presentation.account.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.databinding.FragmentBottomSheetsEditProfileBinding

class BottomSheetsEditProfile : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetsEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBottomSheetsEditProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
