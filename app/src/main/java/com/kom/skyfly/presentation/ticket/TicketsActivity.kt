package com.kom.skyfly.presentation.ticket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import coil.load
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.tickets.TicketsModel
import com.kom.skyfly.data.model.tickets.TransactionDetailModel
import com.kom.skyfly.databinding.ActivityTicketsBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.presentation.main.MainActivity
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketsActivity : BaseActivity() {
    private val binding: ActivityTicketsBinding by lazy {
        ActivityTicketsBinding.inflate(layoutInflater)
    }
    private val ticketViewModel: TicketViewModel by viewModel()
    private var transactionId: String? = null
    var departureDate: String? = null
    var departureTime: String? = null
    var departureAirport: String? = null
    private var departureTerminal: String? = null
    var arrivalDate: String? = null
    var arrivalTime: String? = null
    var destinationAirport: String? = null
    private var airlines: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        transactionId = intent.getStringExtra("EXTRA_TRANSACTION_ID")
        departureDate = intent.getStringExtra("EXTRA_DEPARTURE_DATE")
        departureTime = intent.getStringExtra("EXTRA_DEPARTURE_TIME")
        departureAirport = intent.getStringExtra("EXTRA_DEPARTURE_AIRPORT")
        departureTerminal = intent.getStringExtra("EXTRA_DEPARTURE_TERMINAL")

        arrivalTime = intent.getStringExtra("EXTRA_ARRIVAL_TIME")
        arrivalDate = intent.getStringExtra("EXTRA_ARRIVAL_DATE")
        destinationAirport = intent.getStringExtra("EXTRA_DESTINATION_AIRPORT")
        airlines = intent.getStringExtra("EXTRA_AIRLINE")
        setTitleHeader()
        setOnClickListeners()
        getTicketDetail(transactionId)
    }

    private fun setOnClickListeners() {
        binding.btnToHome.setOnClickListener {
            navigateToHome()
        }
        binding.layoutHeader.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.main.setOnRefreshListener {
            getTicketDetail(transactionId)
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun getTicketDetail(transactionId: String?) {
        if (transactionId != null) {
            ticketViewModel.getTicketById(transactionId).observe(this) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        binding.btnToHome.isEnabled = true
                        binding.layoutFlightDetails.cvFlightDetails.isVisible = true
                        binding.layoutFlightDetails.tvTicketCode.isVisible = true
                        binding.main.isRefreshing = false
                        val response = result.payload
                        binding.shmProgressFlightTicket.isVisible = false
                        setTransactionDetailData(response)
                        if (response?.data?.isEmpty() == true) {
                            binding.btnToHome.isEnabled = true
                            binding.main.isRefreshing = false
                            binding.layoutFlightDetails.cvFlightDetails.isVisible = false
                            binding.layoutFlightDetails.tvTicketCode.isVisible = false
                            binding.shmProgressFlightTicket.isVisible = false
                            binding.csvFlightDetail.setState(
                                ContentState.EMPTY,
                                getString(R.string.text_ticket_is_being_processed),
                                R.drawable.img_loading,
                            )
                        }
                        Log.d("GetTransactionById", "getTransactionDataById: $response")
                    },
                    doOnError = {
                        binding.btnToHome.isEnabled = true
                        binding.main.isRefreshing = false
                        binding.shmProgressFlightTicket.isVisible = false
                        when (it.exception) {
                            is NoInternetException -> {
                                binding.csvFlightDetail.setState(
                                    ContentState.ERROR_NETWORK_GENERAL,
                                    getString(R.string.no_internet_connection),
                                )
                            }

                            is UnAuthorizeException -> {
                                errorHandler(it.exception)
                                binding.csvFlightDetail.setState(
                                    ContentState.ERROR_NETWORK_GENERAL,
                                    getString(R.string.text_session_expired_please_login_again),
                                )
                            }

                            is ServerErrorException -> {
                                errorHandler(it.exception)
                                binding.csvFlightDetail.setState(
                                    ContentState.ERROR_NETWORK,
                                    getString(R.string.text_something_went_wrong),
                                    R.drawable.img_empty_data,
                                )
                            }
                        }
                        Log.d(
                            "GetTransactionByIdError",
                            "getTransactionDataById: ${it.exception?.message}",
                        )
                    },
                    doOnLoading = {
                        binding.csvFlightDetail.isVisible = false
                        binding.btnToHome.isEnabled = false
                        binding.layoutFlightDetails.cvFlightDetails.isVisible = false
                        binding.layoutFlightDetails.tvTicketCode.isVisible = false
                        binding.main.isRefreshing = true
                        binding.shmProgressFlightTicket.isVisible = true
                    },
                    doOnEmpty = {
                        it.payload.let {
                            if (it?.data?.isEmpty() == true) {
                                binding.btnToHome.isEnabled = true
                                binding.main.isRefreshing = false
                                binding.shmProgressFlightTicket.isVisible = false
                                binding.csvFlightDetail.setState(
                                    ContentState.EMPTY,
                                    getString(R.string.text_ticket_is_being_processed),
                                    R.drawable.img_loading,
                                )
                            }
                        }
                        binding.btnToHome.isEnabled = true
                        binding.main.isRefreshing = false
                        binding.shmProgressFlightTicket.isVisible = false
                        binding.csvFlightDetail.setState(
                            ContentState.EMPTY,
                            getString(R.string.text_ticket_is_being_processed),
                            R.drawable.img_loading,
                        )
                    },
                )
            }
        }
    }

    private fun setTransactionDetailData(response: TicketsModel?) {
        var ticketCode: String? = null
        var ticketTransaction: List<TransactionDetailModel?>? = listOf()
        val airlinesImg: String? = null
        response?.data?.map {
            ticketCode = it?.code
            ticketTransaction = it?.ticketTransaction?.transactionDetail
        }

        binding.layoutFlightDetails.tvTitlePassenger.text = getString(R.string.text_empty)
        binding.layoutFlightDetails.tvCitizenship.text = getString(R.string.text_empty)
        binding.layoutFlightDetails.tvFamilyName.text = getString(R.string.text_empty)
        binding.layoutFlightDetails.tvPasspor.text = getString(R.string.text_empty)
        binding.layoutFlightDetails.tvAirline.text = getString(R.string.text_empty)
        binding.layoutFlightDetails.tvSeatClass.text = getString(R.string.text_empty)
        binding.layoutFlightDetails.tvSeatNumber.text = getString(R.string.text_empty)

        with(binding.layoutFlightDetails) {
            ivAirline.load(
                airlinesImg,
            ) {
                error(R.drawable.img_airline)
            }
            tvDetailDepartureDate.text = departureDate
            tvDetailDepartureTime.text = departureTime
            tvDetailDepartureAirport.text =
                getString(R.string.text_departure_terminal, departureAirport, departureTerminal)
            tvTicketCode.text = getString(R.string.text_ticket_code_value, ticketCode)
            tvDetailArrivalDate.text = arrivalDate
            tvDetailArrivalTime.text = arrivalTime
            tvDetailDestinationAirport.text = destinationAirport

            ticketTransaction?.forEachIndexed { index, detail ->
                val passengerName = detail?.name ?: "Unknown"
                val familyName = detail?.familyName ?: "Unknown"
                val citizenship = detail?.citizenship ?: "Unknown"
                val userPassportId = detail?.passport ?: "Unknown"
                val seatClasses = detail?.seat?.type
                val seatNumbers = detail?.seat?.seatNumber

                val isLastItem = index == ticketTransaction!!.size - 1

                tvTitlePassenger.append(
                    "Passenger ${index + 1} : $passengerName\n" +
                        "Family Name : $familyName\n" +
                        "Nationality : $citizenship\n" +
                        "Id / Passport : $userPassportId\n",
                )

                if (isLastItem) {
                    tvAirline.append(
                        "[Passenger ${index + 1}] \n" +
                            "Airline : $airlines\n" +
                            "Seat Number : $seatNumbers\n" +
                            "Seat Class : $seatClasses",
                    )
                } else {
                    tvAirline.append(
                        "[Passenger ${index + 1}] \n" +
                            "Airline : $airlines\n" +
                            "Seat Number : $seatNumbers\n" +
                            "Seat Class : $seatClasses\n\n",
                    )
                }
            }
        }
    }

    private fun setTitleHeader() {
        binding.layoutHeader.tvTitleHeader.text = getString(R.string.text_your_ticket)
    }
}
