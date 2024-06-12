package com.kom.skyfly.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kom.skyfly.databinding.FragmentHomeBinding
import com.kom.skyfly.presentation.home.calendar.HomeCalendarFragment
import com.kom.skyfly.presentation.search.SearchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

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
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.layoutSelectDestination.tiStartFrom.setOnClickListener {
            val searchDeparture = SearchFragment()
            searchDeparture.show(parentFragmentManager, searchDeparture.tag)
        }
        binding.ivDeparture.setOnClickListener {
            val calendarDeparture = HomeCalendarFragment()
            calendarDeparture.show(parentFragmentManager, calendarDeparture.tag)
        }
    }

    companion object{
        const val EXTRAS_DESTINATION = "EXTRAS_DESTINATION"
    }
}
