package com.kom.skyfly.presentation.history.searchflighthistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentSearchFlightHistoryBinding

class SearchFlightHistoryFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSearchFlightHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchFlightHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()

        val maxPeekHeight =
            resources.getDimensionPixelSize(
                R.dimen.max_bottom_sheet_height,
            )
        setBottomSheetMaxHeight(maxPeekHeight)
    }

    private fun setBottomSheetMaxHeight(maxHeight: Int) {
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as? BottomSheetDialog
            val bottomSheet = bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                behavior.peekHeight = maxHeight
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun setOnClickListener() {
        binding.layoutSearchBar.ivCloseSearch.setOnClickListener {
            dismiss()
        }
    }
}