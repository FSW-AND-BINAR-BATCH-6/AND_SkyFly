package com.kom.skyfly.presentation.bottomsheetsdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.databinding.FragmentBottomSheetsDialogBinding

class BottomSheetsDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetsDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBottomSheetsDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
