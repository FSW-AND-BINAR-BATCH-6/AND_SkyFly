package com.kom.skyfly.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.kom.skyfly.R
import com.kom.skyfly.data.model.destinationfavorite.DestinationFavorite
import com.kom.skyfly.databinding.FragmentHomeBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.presentation.home.adapter.DestinationFavoriteAdapter
import com.kom.skyfly.presentation.home.calendar.HomeCalendarFragment
import com.kom.skyfly.presentation.home.passenger.PassengerFragment
import com.kom.skyfly.presentation.home.search.SearchFragment
import com.kom.skyfly.presentation.home.search_result.SearchResultActivity
import com.kom.skyfly.presentation.home.seatclass.SeatClassFragment
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
    private var source: String = ""
    private var dest: String = ""

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
        setRoundTrip()
        observeDataDestination()
        setOnClickListener()
        setupDestinationFavorite()
        getDestinationFavoriteData()
    }

    @SuppressLint("SetTextI18n")
    private fun observeDataDestination() {
        sharedViewModel.sourceDestination.observe(viewLifecycleOwner) { destination ->
            destination?.let {
                if (sharedViewModel.isStartDestination!!) {
                    binding.layoutSelectDestination.tvStartFrom.text = "${it.city} (${it.code})"
                    source = binding.layoutSelectDestination.tvStartFrom.text.toString()
                } else {
                    binding.layoutSelectDestination.tvEndDestination.text =
                        "${it.city} (${it.code})"
                    dest = binding.layoutSelectDestination.tvEndDestination.text.toString()
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
                binding.tvPassengers.text = "$it Passengers"
            }
        }
    }

    fun convertDateFormat(inputDate: String): String? {
        val inputFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale("id", "ID"))

        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return try {
            val date = LocalDate.parse(inputDate, inputFormatter)
            date.format(outputFormatter)
        } catch (e: DateTimeParseException) {
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
            if (binding.tvReturn.isEnabled) {
                val calendarDeparture = HomeCalendarFragment()
                calendarDeparture.show(parentFragmentManager, calendarDeparture.tag)
            }
        }
        binding.tvPassengers.setOnClickListener {
            val passengerBottomSheet = PassengerFragment()
            passengerBottomSheet.show(parentFragmentManager, passengerBottomSheet.tag)
        }
        binding.btnSearchFlight.setOnClickListener {
            val departureAirport = binding.layoutSelectDestination.tvStartFrom.text
            val arrivalAirport = binding.layoutSelectDestination.tvEndDestination.text
            val babyCount = sharedViewModel.passengerBabyCountLiveData
            val adultCount = sharedViewModel.passengerAdultCountLiveData
            val childCount = sharedViewModel.passengerChildCountLiveData
            val departureTime = convertDateFormat(binding.tvDeparture.text.toString())

            val intent =
                Intent(requireContext(), SearchResultActivity::class.java).apply {
                    putExtra("EXTRA_DEPARTURE_AIRPORT", departureAirport)
                    putExtra("EXTRA_ARRIVAL_AIRPORT", arrivalAirport)
                    putExtra("EXTRA_DEPARTURE_TIME", departureTime)
                    putExtra("EXTRA_BABY_COUNT", babyCount.value)
                    putExtra("EXTRA_ADULT_COUNT", adultCount.value)
                    putExtra("EXTRA_CHILD_COUNT", childCount.value)
                }
            startActivity(intent)
        }
        binding.layoutSelectDestination.ivSwap.setOnClickListener {
            binding.layoutSelectDestination.tvStartFrom.text = dest
            binding.layoutSelectDestination.tvEndDestination.text = source
            val temp = source
            source = dest
            dest = temp
        }
        binding.tvSeats.setOnClickListener {
            val seatClassBottomSheet = SeatClassFragment()
            seatClassBottomSheet.show(parentFragmentManager, seatClassBottomSheet.tag)
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

    private fun setRoundTrip() {
        binding.btnSwitchRoundtrip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tvReturn.isEnabled = true
                binding.tvReturn.text = getString(R.string.text_select_dates)
                binding.tvReturn.setTextColor(resources.getColor(R.color.md_theme_primary))
            } else {
                binding.tvReturn.isEnabled = false
                binding.tvReturn.text = getString(R.string.text_strips)
                binding.tvReturn.setTextColor(resources.getColor(R.color.darkGrey))
            }
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
