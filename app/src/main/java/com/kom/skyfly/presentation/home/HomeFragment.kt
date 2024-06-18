package com.kom.skyfly.presentation.home

import android.content.Intent
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
import com.kom.skyfly.presentation.home.passenger.PassengerFragment
import com.kom.skyfly.presentation.home.search.SearchFragment
import com.kom.skyfly.presentation.home.search_result.SearchResultActivity
import com.kom.skyfly.presentation.main.MainViewModel
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val sharedViewModel: MainViewModel by activityViewModel()
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
        observeDataDestination()
        setOnClickListener()
        setupDestinationFavorite()
        getDestinationFavoriteData()
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
        sharedViewModel.passengerCountLiveData.observe(viewLifecycleOwner) { totalPassenger ->
            totalPassenger?.let {
                binding.tvPassengers.text = it.toString()
            }
        }
    }

    fun convertDateFormat(inputDate: String): String? {
        // Define the formatter for the input date format
        val inputFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale("id", "ID"))

        // Define the formatter for the output date format
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return try {
            // Parse the input date string to a LocalDate object
            val date = LocalDate.parse(inputDate, inputFormatter)

            // Format the LocalDate object to the output format
            date.format(outputFormatter)
        } catch (e: DateTimeParseException) {
            // Handle the exception if the input date string is not in the expected format
            e.printStackTrace()
            null
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
        binding.btnSearchFlight.setOnClickListener {
            val departureAirport = binding.layoutSelectDestination.tvStartFrom.text
            val arrivalAirport = binding.layoutSelectDestination.tvEndDestination.text
            val departureTime = convertDateFormat(binding.tvDeparture.text.toString())

            val intent =
                Intent(requireContext(), SearchResultActivity::class.java).apply {
                    putExtra("EXTRA_DEPARTURE_AIRPORT", departureAirport)
                    putExtra("EXTRA_ARRIVAL_AIRPORT", arrivalAirport)
                    putExtra("EXTRA_DEPARTURE_TIME", departureTime)
                }
            startActivity(intent)
        }
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

    companion object {
        const val EXTRAS_DESTINATION = "EXTRAS_DESTINATION"
    }
}
