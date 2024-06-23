package com.kom.skyfly.presentation.checkout.chooseseat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.data.model.passenger.PassengerData
import com.kom.skyfly.data.source.network.model.transaction.request.Bookers
import com.kom.skyfly.data.source.network.model.transaction.request.TransactionRequest
import com.kom.skyfly.databinding.ActivityChooseSeatBinding
import com.kom.skyfly.presentation.checkout.bookersbiodata.BookersBiodataActivity
import com.kom.skyfly.presentation.checkout.checkoutticket.CheckoutTicketActivity
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.proceedWhen
import dev.jahidhasanco.seatbookview.SeatBookView
import dev.jahidhasanco.seatbookview.SeatClickListener
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseSeatActivity : AppCompatActivity() {
    private val binding: ActivityChooseSeatBinding by lazy {
        ActivityChooseSeatBinding.inflate(layoutInflater)
    }
    private val chooseSeatViewModel: ChooseSeatViewModel by viewModel()

    private var seats: String = ""
    private var titles = listOf<String>()
    private lateinit var passengerDataList: List<PassengerData>

    private lateinit var seatBookView: SeatBookView
    private var seatTotal: Int = 0
    private var seatType: String = ""
    private var seatPrice = listOf<Int?>()
    private var seatId = listOf<String?>()

    private var email: String? = null
    private var fullNames: String? = null
    private var familyName: String? = null
    private var phoneNumber: String? = null
    private var flightId: String? = null

    private var currentPassengerIndex = 0

    private var adult: Int = 0
    private var children: Int = 0
    private var baby: Int = 0
    private var paymentUrl: String? = null
    private var transactionId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fullNames = intent.getStringExtra("EXTRAS_FULL_NAME")
        familyName = intent.getStringExtra("EXTRAS_FAMILY_NAME")
        email = intent.getStringExtra("EXTRAS_EMAIL")
        phoneNumber = intent.getStringExtra("EXTRAS_PHONE_NUMBER")
        passengerDataList = intent.getParcelableArrayListExtra("EXTRAS_PASSENGERS")!!
        adult = intent.getIntExtra("EXTRAS_ADULT", 0)
        children = intent.getIntExtra("EXTRAS_CHILD", 0)
        baby = intent.getIntExtra("EXTRAS_BABY", 0)
        passengerDataList.forEachIndexed { index, passenger ->
            Log.d("PassengerData", "Passenger $index: $passenger")
        }
        setTitleHeader()
        flightId = "clxrh0w6m000zokfpvb5sobt8"
        getAllFlightSeatData(flightId!!)
        getFlightSeatData(flightId!!)
        getFlightPrice(flightId!!)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnSave.setOnClickListener {
            flightId?.let { createTransaction(it, passengerDataList) }
        }
    }

    private fun getFlightSeatData(flightId: String) {
        chooseSeatViewModel.getFlightSeat(flightId).observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = { resultWrapper ->
                    val payload = resultWrapper.payload
                    chooseSeatViewModel.setSeatList(payload?.flightSeat.orEmpty())
                    if (payload != null) {
                        val (status, title) = payload.seatLabelling
                        seats = status
                        titles = title
                        setSeatView()
                    } else {
                        Log.e("ChooseSeatActivity", "Error: Payload is null")
                    }
                    binding.shmProgressSeatView.isVisible = false
                    binding.csvSeatView.setState(ContentState.SUCCESS)
                },
                doOnEmpty = {
                    binding.shmProgressSeatView.isVisible = false
                    binding.csvSeatView.setState(ContentState.EMPTY, "Empty seat!")
                },
                doOnError = { error ->
                    binding.shmProgressSeatView.isVisible = false
                    if (error.exception is NoInternetException) {
                        binding.csvSeatView.setState(ContentState.ERROR_NETWORK)
                    } else {
                        binding.csvSeatView.setState(ContentState.ERROR_GENERAL)
                    }
                    Log.e("ChooseSeatActivity", "Error: ${error.exception?.message}")
                },
                doOnLoading = {
                    binding.shmProgressSeatView.isVisible = true
                },
            )
        }
    }

    private fun getAllFlightSeatData(flightId: String) {
        chooseSeatViewModel.getAllFlightSeat(flightId).observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = { resultWrapper ->
                    Log.d("SeatDatas", "getAllFlightSeatData: $resultWrapper")
                    seatTotal = resultWrapper.payload?.size ?: 0
                    resultWrapper.payload.let {
                        it?.map { response ->
                            seatType = response.type.orEmpty()
                            binding.tvSeatTitle.text = "$seatType - $seatTotal Seat"
                        }
                    }
                },
                doOnLoading = {
                    binding.tvSeatTitle.text = "Loading..."
                },
                doOnEmpty = {
                    binding.tvSeatTitle.text = "SeatView Kosong"
                },
                doOnError = {
                    binding.tvSeatTitle.text = "Error"
                },
            )
        }
    }

    private fun getFlightPrice(flightId: String) {
        chooseSeatViewModel.getFlightPrice(flightId).observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    val payload = it.payload
                    payload?.let { response ->
                        seatId = response.first
                        seatPrice = response.second
                    }
                },
            )
        }
    }

    private fun setSeatView() {
        seatBookView = binding.layoutSeat
        seatBookView.setSeatsLayoutString(seats)
            .isCustomTitle(true)
            .setCustomTitle(titles)
            .setSelectSeatLimit(passengerDataList.size)
            .setSeatLayoutPadding(2)
            .setSeatSizeBySeatsColumnAndLayoutWidth(7, -1)

        seatBookView.show()
        seatBookView.setSeatClickListener(
            object : SeatClickListener {
                override fun onAvailableSeatClick(
                    selectedIdList: List<Int>,
                    view: View,
                ) {
                    val actualSelectedIdx = selectedIdList.map { it - 1 }
                    chooseSeatViewModel.setSelectedSeatList(actualSelectedIdx)

                    if (currentPassengerIndex >= passengerDataList.size) {
                        Toasty.info(
                            this@ChooseSeatActivity,
                            "All passengers have been assigned seats.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        return
                    }

                    val selectedSeatIndex =
                        selectedIdList.firstOrNull()
                    if (selectedSeatIndex == null) {
                        Toasty.error(
                            this@ChooseSeatActivity,
                            "No seat selected.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        return
                    }
                    val correctedSeatIndex = selectedSeatIndex - 1

                    if (correctedSeatIndex < 0 || correctedSeatIndex >= seatId.size) {
                        Toasty.error(
                            this@ChooseSeatActivity,
                            "Invalid seat selected.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        return
                    }

                    val selectedSeatId = seatId[correctedSeatIndex]
                    val currentPassenger = passengerDataList[currentPassengerIndex]

                    Toasty.success(
                        this@ChooseSeatActivity,
                        "Assigned seat $selectedSeatId to ${currentPassenger.fullName}",
                        Toast.LENGTH_SHORT,
                    ).show()

                    currentPassengerIndex++
                    if (currentPassengerIndex < passengerDataList.size) {
                        Toasty.warning(
                            this@ChooseSeatActivity,
                            "Select seat for ${passengerDataList[currentPassengerIndex].fullName}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        Toasty.info(
                            this@ChooseSeatActivity,
                            "All passengers have been assigned seats.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

                override fun onBookedSeatClick(view: View) {
                    Toasty.info(
                        this@ChooseSeatActivity,
                        "Seat is Booked!",
                        Toast.LENGTH_SHORT,
                    ).show()
                }

                override fun onReservedSeatClick(view: View) {
                    Toasty.info(
                        this@ChooseSeatActivity,
                        "Seat is Reserved!",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            },
        )
    }

    private fun createTransaction(
        flightId: String,
        passengers: List<PassengerData>,
    ) {
        val bookers =
            Bookers(
                fullName = fullNames ?: "",
                familyName = familyName ?: "",
                phoneNumber = phoneNumber ?: "",
                email = email ?: "",
            )

        val transactionRequest =
            TransactionRequest(
                orderer = bookers,
                passengers = chooseSeatViewModel.mapPassengersToSeat(passengers),
            )

        chooseSeatViewModel.createTransaction(flightId, adult, children, baby, transactionRequest)
            .observe(this) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        Toasty.success(this, "Success Create Transaction!!!!!", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this, BookersBiodataActivity::class.java)
                        startActivity(intent)
                        it.payload?.let { response ->
                            paymentUrl = response.redirectUrl
                            transactionId = response.transactionId
                        }
                        navigateToPayment()
                    },
                    doOnError = {
                        Log.e("ChooseSeatActivity", "Error: ${it.exception?.message}")
                    },
                    doOnLoading = {
                        binding.pbLoading.isVisible = true
                        binding.btnSave.isVisible = false
                    },
                )
            }
    }

    private fun setTitleHeader() {
        binding.layoutHeader.tvTitleHeader.text = getString(R.string.text_header_choose_seat)
    }

    private fun navigateToPayment() {
        val intent = Intent(this, CheckoutTicketActivity::class.java)
        intent.putExtra("EXTRAS_PAYMENT_URL", paymentUrl)
        intent.putExtra("EXTRAS_TRANSACTION_ID", transactionId)
        intent.putExtra("EXTRAS_ADULT", adult)
        intent.putExtra("EXTRAS_CHILD", children)
        intent.putExtra("EXTRAS_BABY", baby)
        startActivity(intent)
    }
}
