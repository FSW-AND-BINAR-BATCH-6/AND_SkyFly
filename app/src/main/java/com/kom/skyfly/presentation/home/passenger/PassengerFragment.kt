package com.kom.skyfly.presentation.home.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.databinding.FragmentPassengerBinding
import com.kom.skyfly.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PassengerFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPassengerBinding
    private val mainViewModel: MainViewModel by activityViewModel()

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
        observeData()
    }

    private fun observeData() {
        mainViewModel.passengerAdultCountLiveData.observe(viewLifecycleOwner) {
            binding.tvCounterAdult.text = it.toString()
            binding.ivMinusBtnAdult.isEnabled = it > 1
        }
        mainViewModel.passengerChildCountLiveData.observe(viewLifecycleOwner) {
            binding.tvCounterChild.text = it.toString()
            binding.ivMinusBtnChild.isEnabled = it > 0
        }
        mainViewModel.passengerBabyCountLiveData.observe(viewLifecycleOwner) {
            binding.tvCounterBaby.text = it.toString()
            binding.ivMinusBtnBaby.isEnabled = it > 0
        }
    }

    private fun setOnclickListener() {
        binding.ivClosePassengerPage.setOnClickListener {
            dismiss()
        }
        binding.ivBtnPlusAdult.setOnClickListener {
            mainViewModel.addAdult()
        }
        binding.ivBtnPlusChild.setOnClickListener {
            mainViewModel.addChild()
        }
        binding.ivBtnPlusBaby.setOnClickListener {
            mainViewModel.addBaby()
        }
        binding.ivMinusBtnAdult.setOnClickListener {
            mainViewModel.minusAdult()
        }
        binding.ivMinusBtnChild.setOnClickListener {
            mainViewModel.minusChild()
        }
        binding.ivMinusBtnBaby.setOnClickListener {
            mainViewModel.minusBaby()
        }
        binding.btnSaveTotalPassenger.setOnClickListener {
            mainViewModel.addTotal()
            dismiss()
        }
    }
}
