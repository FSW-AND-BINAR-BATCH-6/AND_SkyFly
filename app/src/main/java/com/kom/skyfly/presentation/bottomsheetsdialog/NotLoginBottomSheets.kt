package com.kom.skyfly.presentation.bottomsheetsdialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentBottomSheetsDialogBinding
import com.kom.skyfly.presentation.login.LoginActivity
import com.kom.skyfly.presentation.main.MainActivity

class NotLoginBottomSheets : BottomSheetDialogFragment() {
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
        val maxPeekHeight =
            resources.getDimensionPixelSize(
                R.dimen.max_bottom_sheet_height,
            )
        setBottomSheetMaxHeight(maxPeekHeight)
    }

    private fun setClickListeners() {
        binding.btnLogin.setOnClickListener {
            navigateToLogin()
            listener?.onClose()
            dismiss()
        }
        binding.ivClose.setOnClickListener {
            navigateToHome()
            listener?.onClose()
            dismiss()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }

    private fun setBottomSheetMaxHeight(maxHeight: Int) {
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as? BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                behavior.peekHeight = maxHeight
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }
}
