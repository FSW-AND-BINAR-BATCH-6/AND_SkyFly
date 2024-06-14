package com.kom.skyfly.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kom.skyfly.databinding.FragmentHomeBinding
import com.kom.skyfly.presentation.home.calendar.HomeCalendarFragment
import com.kom.skyfly.presentation.home.passenger.PassengerFragment
import com.kom.skyfly.presentation.home.search.SearchFragment
import com.kom.skyfly.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val sharedViewModel: MainViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.setOnBoardingShow(true)
        observeDataDestination()
        setOnClickListener()
    }

    private fun observeDataDestination() {
        sharedViewModel.sourceDestination.observe(viewLifecycleOwner) { destination ->
            destination?.let {
                if (sharedViewModel.isStartDestination!!) {
                    binding.layoutSelectDestination.tvStartFrom.text = it.city
                } else {
                    binding.layoutSelectDestination.tvEndDestination.text = it.city
                }
            }
        }
        sharedViewModel.startTime.observe(viewLifecycleOwner) { startTIme ->
            startTIme?.let {
                binding.tvDeparture.text = it
            }
        }
        sharedViewModel.returnTime.observe(viewLifecycleOwner) { returnTime ->
            returnTime?.let {
                binding.tvReturn.text = it
            }
        }
    }

    private fun setOnClickListener() {
        binding.layoutSelectDestination.tvStartFrom.setOnClickListener {
            sharedViewModel.isStartDestination = true
            val searchDeparture = SearchFragment()
            searchDeparture.show(parentFragmentManager, searchDeparture.tag)
        }
        binding.layoutSelectDestination.tiEndDestination.setOnClickListener {
            sharedViewModel.isStartDestination = false
            val searchDeparture = SearchFragment()
            searchDeparture.show(parentFragmentManager, searchDeparture.tag)
        }
        binding.tvDeparture.setOnClickListener {
            val calendarDeparture = HomeCalendarFragment()
            calendarDeparture.show(parentFragmentManager, calendarDeparture.tag)
        }
        binding.tvReturn.setOnClickListener {
            val calendarDeparture = HomeCalendarFragment()
            calendarDeparture.show(parentFragmentManager, calendarDeparture.tag)
        }
        binding.tvPassengers.setOnClickListener {
            val passengerBottomSheet = PassengerFragment()
            passengerBottomSheet.show(parentFragmentManager, passengerBottomSheet.tag)
        }
    }

    companion object {
        const val EXTRAS_DESTINATION = "EXTRAS_DESTINATION"
    }
}
