package com.kom.skyfly.presentation.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.home.destination_favourite.DestinationFavourite
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
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val sharedViewModel: MainViewModel by activityViewModel()
    private var source: String = ""
    private var dest: String = ""
    private var departureAirport: String? = null
    private var arrivalAirport: String? = null
    private val destinationAdapter: DestinationFavoriteAdapter by lazy {
        DestinationFavoriteAdapter { item ->
            item.let {
                binding.layoutSelectDestination.tvStartFrom.text = it.departureCity
                binding.layoutSelectDestination.tvEndDestination.text = it.arrivalCity
                source = it.departureCity!!
                dest = it.arrivalCity!!
                departureAirport = it.departureCity
                arrivalAirport = it.arrivalCity
                showMessageBox()
            }
        }
    }

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
        setOnRefresh()
        observeDataDestination()
        setOnClickListener()
        setupDestinationFavorite()
        getDestinationFavoriteData()
        validateFields()
    }

    private fun setOnRefresh() {
        binding.rlHome.setOnRefreshListener {
            observeDataDestination()
            getDestinationFavoriteData()
        }
    }

    fun showMessageBox() {
        // Inflate the dialog as custom view
        val messageBoxView =
            LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog, null)

        // AlertDialogBuilder
        val messageBoxBuilder = AlertDialog.Builder(requireContext()).setView(messageBoxView)

        // Setting text values
        val headerTextView = messageBoxView.findViewById<TextView>(R.id.message_box_header)
        val contentTextView = messageBoxView.findViewById<TextView>(R.id.message_box_content)
        headerTextView.text = getString(R.string.text_success)
        contentTextView.text = getString(R.string.text_destination_successfully_added)

        // Show dialog
        val messageBoxInstance = messageBoxBuilder.show()

        // Set Listener
        messageBoxView.setOnClickListener {
            // Close dialog
            messageBoxInstance.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeDataDestination() {
        sharedViewModel.sourceDestination.observe(viewLifecycleOwner) { destination ->
            destination?.let {
                if (sharedViewModel.isStartDestination!!) {
                    binding.layoutSelectDestination.tvStartFrom.text = it.city
                    source = binding.layoutSelectDestination.tvStartFrom.text.toString()
                    departureAirport = it.city
                } else {
                    binding.layoutSelectDestination.tvEndDestination.text = it.city
                    dest = binding.layoutSelectDestination.tvEndDestination.text.toString()
                    arrivalAirport = it.city
                }
            }
            validateFields()
        }
        sharedViewModel.startTime.observe(viewLifecycleOwner) { startTIme ->
            startTIme?.let {
                binding.tvDeparture.text = it
            }
            validateFields()
        }
        sharedViewModel.returnTime.observe(viewLifecycleOwner) { returnTime ->
            returnTime?.let {
                binding.tvReturn.text = it
            }
            validateFields()
        }
        sharedViewModel.passengerCountLiveData.observe(viewLifecycleOwner) { totalPassenger ->
            totalPassenger?.let {
                if (it > 0) {
                    binding.tvPassengers.text = "$it Passengers"
                } else {
                    "Add Passenger"
                }
            }
            validateFields()
        }
        sharedViewModel.seatClass.observe(viewLifecycleOwner) { seatClass ->
            seatClass?.let {
                binding.tvSeats.text = it
            }
            validateFields()
        }
    }

    private fun convertDateFormat(inputDate: String): String? {
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
//        binding.tvReturn.setOnClickListener {
//            if (binding.tvReturn.isEnabled) {
//                val calendarDeparture = HomeCalendarFragment()
//                calendarDeparture.show(parentFragmentManager, calendarDeparture.tag)
//            }
//        }
        binding.tvPassengers.setOnClickListener {
            val passengerBottomSheet = PassengerFragment()
            passengerBottomSheet.show(parentFragmentManager, passengerBottomSheet.tag)
        }
        binding.btnSearchFlight.setOnClickListener {
            val babyCount = sharedViewModel.passengerBabyCountLiveData
            val adultCount = sharedViewModel.passengerAdultCountLiveData
            val childCount = sharedViewModel.passengerChildCountLiveData
            val seatClass = sharedViewModel.seatClass
            val roundTrip = sharedViewModel.roundTrip
            val returnDate = convertDateFormat(binding.tvReturn.text.toString())
            val departureTime = convertDateFormat(binding.tvDeparture.text.toString())

            val intent =
                Intent(requireContext(), SearchResultActivity::class.java).apply {
                    putExtra("EXTRA_DEPARTURE_AIRPORT", departureAirport)
                    putExtra("EXTRA_ARRIVAL_AIRPORT", arrivalAirport)
                    putExtra("EXTRA_RETURN_TIME", returnDate)
                    putExtra("EXTRA_DEPARTURE_TIME", departureTime)
                    putExtra("EXTRA_BABY_COUNT", babyCount.value)
                    putExtra("EXTRA_ADULT_COUNT", adultCount.value)
                    putExtra("EXTRA_CHILD_COUNT", childCount.value)
                    putExtra("EXTRA_SEAT_CLASS", seatClass.value)
                    putExtra("EXTRA_TOTAL_PASSENGER", sharedViewModel.passengerCountLiveData.value)
                    putExtra("EXTRA_ROUND_TRIP", roundTrip.value)
                }
            startActivity(intent)
        }
        binding.layoutSelectDestination.ivSwap.setOnClickListener {
            // Store the current values of source and dest
            val tempSource = source
            val tempDest = dest

            // Update source with the value of dest
            source = tempDest

            // Update dest with the value of tempSource (initial value of source)
            dest = tempSource

            // Update TextViews with the new values
            binding.layoutSelectDestination.tvStartFrom.text = source
            binding.layoutSelectDestination.tvEndDestination.text = dest

            // Update class variables
            departureAirport = source
            arrivalAirport = dest
        }

        binding.tvSeats.setOnClickListener {
            val seatClassBottomSheet = SeatClassFragment()
            seatClassBottomSheet.show(parentFragmentManager, seatClassBottomSheet.tag)
        }
    }

    private fun validateFields() {
        val isSourceValid = binding.layoutSelectDestination.tvStartFrom.text.isNotEmpty()
        val isDestinationValid =
            binding.layoutSelectDestination.tvEndDestination.text.isNotEmpty()
        val isDepartureTimeValid = binding.tvDeparture.text.isNotEmpty()
//        val isReturnTimeValid =
//            !binding.tvReturn.isEnabled || binding.tvReturn.text.toString().isNotEmpty()
        val isPassengerCountValid = binding.tvPassengers.text.isNotEmpty()
        val isSeatClassValid = binding.tvSeats.text.isNotEmpty()

        binding.btnSearchFlight.isEnabled =
            isSourceValid && isDestinationValid && isDepartureTimeValid && isPassengerCountValid && isSeatClassValid
    }

    private fun getDestinationFavoriteData() {
        homeViewModel.getAllDestinationFavorite().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rlHome.isRefreshing = false
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
                    binding.rlHome.isRefreshing = false
                    binding.csvHome.setState(ContentState.EMPTY)
                    binding.shmProgressDestinationFav.isVisible = false
                },
                doOnError = { error ->
                    binding.rlHome.isRefreshing = false
                    binding.rvCategory.isVisible = false
                    binding.shmProgressDestinationFav.isVisible = false
                    if (error.exception is NoInternetException) {
                        binding.csvHome.setState(
                            ContentState.ERROR_NETWORK,
                            getString(R.string.no_internet_connection),
                        )
                    } else if (error.exception is UnAuthorizeException) {
                        (activity as BaseActivity).errorHandler(error.exception)
                        binding.csvHome.setState(
                            ContentState.ERROR_NETWORK_GENERAL,
                            getString(R.string.text_session_expired_please_login_again),
                        )
                    } else if (error.exception is ServerErrorException) {
                        (activity as BaseActivity).errorHandler(error.exception)
                        binding.csvHome.setState(
                            ContentState.ERROR_NETWORK,
                            getString(R.string.text_server_error_please_try_again_later),
                            R.drawable.img_empty_data,
                        )
                    } else {
                        binding.csvHome.setState(ContentState.ERROR_GENERAL)
                    }
                },
            )
        }
    }

    private fun setRoundTrip() {
        binding.btnSwitchRoundtrip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedViewModel.setRoundTrip(true)
                binding.tvReturn.isEnabled = true
                binding.tvReturn.text = getString(R.string.text_select_dates)
                val trackDrawable = binding.btnSwitchRoundtrip.trackDrawable
                trackDrawable?.setTint(resources.getColor(R.color.md_theme_primaryFixed_mediumContrast))
                binding.tvReturn.setTextColor(resources.getColor(R.color.md_theme_primary))
            } else {
                sharedViewModel.setRoundTrip(false)
                binding.tvReturn.isEnabled = false
                binding.tvReturn.text = getString(R.string.text_strips)
                binding.tvReturn.setTextColor(resources.getColor(R.color.darkGrey))
                val trackDrawable = binding.btnSwitchRoundtrip.trackDrawable
                trackDrawable?.setTint(resources.getColor(R.color.grey))
            }
        }
    }

    private fun bindDestinationFavoriteList(data: List<DestinationFavourite>) {
        destinationAdapter.submitData(data)
    }

    private fun setupDestinationFavorite() {
        binding.rvCategory.apply {
            adapter = destinationAdapter
        }
    }
}
