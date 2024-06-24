package com.kom.skyfly.presentation.home.seatclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.data.model.home.seat_class.SeatClassHome
import com.kom.skyfly.databinding.FragmentSeatClassBinding
import com.kom.skyfly.presentation.home.seatclass.adapter.SeatClassAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeatClassFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSeatClassBinding
    private val seatClassViewModel: SeatClassViewModel by viewModel()

    private val adapter: SeatClassAdapter by lazy {
        SeatClassAdapter {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSeatClassBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(seatClassViewModel.getSeatClass())
        setOnClickListener()
    }

    private fun setupBinding(data: List<SeatClassHome>) {
        binding.rvSeatclassCategories.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@SeatClassFragment.adapter
        }
        adapter.submitData(data)
    }

    private fun setOnClickListener() {
    }
}
