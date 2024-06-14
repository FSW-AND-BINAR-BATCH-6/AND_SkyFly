package com.kom.skyfly.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.kom.skyfly.data.model.destinationfavorite.DestinationFavorite
import com.kom.skyfly.databinding.FragmentHomeBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.presentation.home.adapter.DestinationFavoriteAdapter
import com.kom.skyfly.presentation.home.calendar.HomeCalendarFragment
import com.kom.skyfly.presentation.search.SearchFragment
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val destinationAdapter: DestinationFavoriteAdapter by lazy { DestinationFavoriteAdapter {} }

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
        setupDestinationFavorite()
        getDestinationFavoriteData()
    }

    private fun getDestinationFavoriteData() {
        homeViewModel.getAllDestinationFavorite().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvCategory.isVisible = true
                    binding.shmProgressDestinationFav.isVisible = false
                    binding.csvHome.setState(ContentState.SUCCESS)
                    it.payload?.let {
                        bindDestinationFavoriteList(it)
                    }
                },
                doOnLoading = {
                    binding.rvCategory.isVisible = false
                    binding.shmProgressDestinationFav.isVisible = true
                },
                doOnEmpty = {
                    binding.csvHome.setState(ContentState.EMPTY)
                    binding.shmProgressDestinationFav.isVisible = false
                },
                doOnError = {
                    if (it.exception is NoInternetException) {
                        binding.csvHome.setState(ContentState.ERROR_NETWORK)
                    } else {
                        binding.csvHome.setState(ContentState.ERROR_GENERAL)
                    }
                    binding.shmProgressDestinationFav.isVisible = false
                },
            )
        }
    }

    private fun bindDestinationFavoriteList(data: List<DestinationFavorite>) {
        destinationAdapter.submitData(data)
    }

    private fun setupDestinationFavorite() {
        binding.rvCategory.apply {
            adapter = destinationAdapter
        }
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

    companion object {
        const val EXTRAS_DESTINATION = "EXTRAS_DESTINATION"
    }
}
