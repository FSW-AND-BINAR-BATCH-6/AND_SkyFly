package com.kom.skyfly.presentation.bottomsheetsdialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.databinding.FragmentBottomSheetsDialogBinding
import com.kom.skyfly.presentation.login.LoginActivity

class BottomSheetsDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetsDialogBinding
    private var listener: SearchView.OnCloseListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBottomSheetsDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnLogin.setOnClickListener {
            navigateToLogin()
        }
        binding.ivClose.setOnClickListener {
            listener?.onClose()
            dismiss()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }
}
