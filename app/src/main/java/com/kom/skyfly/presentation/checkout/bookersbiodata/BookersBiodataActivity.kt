package com.kom.skyfly.presentation.checkout.bookersbiodata

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.home.flight_detail.FlightDetailTicket
import com.kom.skyfly.databinding.ActivityOrdererBiodataBinding
import com.kom.skyfly.presentation.checkout.passengerbiodata.PassengerBiodataActivity
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookersBiodataActivity : BaseActivity() {
    private val binding: ActivityOrdererBiodataBinding by lazy {
        ActivityOrdererBiodataBinding.inflate(layoutInflater)
    }
    private val bookersBiodataViewModel: BookersBiodataViewModel by viewModel()
    private var email: String? = null
    private var fullName: String? = null
    private var familyName: String? = null
    private var phoneNumber: String? = null
    private var id: String? = null
    private var flightDetailTicket: FlightDetailTicket? = null
    private var adultCount: Int? = 0
    private var childCount: Int? = 0
    private var babyCount: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        flightDetailTicket = intent.getParcelableExtra("EXTRAS_FLIGHT_DETAIL")
        adultCount = intent.getIntExtra("EXTRA_ADULT_COUNT", 0)
        childCount = intent.getIntExtra("EXTRA_CHILD_COUNT", 0)
        babyCount = intent.getIntExtra("EXTRA_BABY_COUNT", 0)
        setHaveFamilyName()
        setTitleHeader()
        getProfileData()
        setActionListeners()
    }

    private fun navigateToPassengerBiodata() {
        val intent =
            Intent(this, PassengerBiodataActivity::class.java).apply {
                putExtra("EXTRAS_FLIGHT_DATA", flightDetailTicket)
                putExtra("EXTRAS_FULL_NAME", fullName)
                putExtra("EXTRAS_FAMILY_NAME", familyName)
                putExtra("EXTRAS_EMAIL", email)
                putExtra("EXTRAS_PHONE_NUMBER", phoneNumber)
                putExtra("EXTRA_ADULT_COUNT", adultCount)
                putExtra("EXTRA_CHILD_COUNT", childCount)
                putExtra("EXTRA_BABY_COUNT", babyCount)
            }
        startActivity(intent)
    }

    private fun setActionListeners() {
        binding.main.setOnRefreshListener {
            getProfileData()
        }
        binding.btnSave.setOnClickListener {
            navigateToPassengerBiodata()
        }
        binding.layoutHeader.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setTitleHeader() {
        binding.layoutHeader.tvTitleHeader.text = getString(R.string.text_customer_biodata_title)
    }

    private fun setHaveFamilyName() {
        with(binding.layoutFormCustomerBiodata) {
            scHaveFamilyName.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    tvFamilyName.isVisible = true
                    tilFamilyName.isVisible = true
                    etFamilyName.isVisible = true
                    tvFamilyName.isVisible = true
                } else {
                    tvFamilyName.isVisible = false
                    tilFamilyName.isVisible = false
                    etFamilyName.isVisible = false
                    tvFamilyName.isVisible = false
                }
            }
        }
    }

    private fun getProfileData() {
        bookersBiodataViewModel.getProfile().observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.btnSave.isEnabled = true
                    it.payload.let { data ->
                        id = data?.userId
                        email = data?.email
                        familyName = data?.familyName
                        fullName = data?.fullName
                        phoneNumber = data?.phoneNumber
                        binding.layoutHeader.tvTitleHeader.text =
                            getString(R.string.text_bookers_biodata)
                        binding.layoutFormCustomerBiodata.etFullName.setText(data?.fullName)
                        binding.layoutFormCustomerBiodata.etEmail.setText(data?.email)
                        binding.layoutFormCustomerBiodata.etNoTlp.setText(data?.phoneNumber)
                        binding.layoutFormCustomerBiodata.etFamilyName.setText(data?.familyName)
                        binding.main.isRefreshing = false
                    }
                },
                doOnError = {
                    binding.btnSave.isEnabled = false
                    binding.main.isRefreshing = false
                    if (it.exception is NoInternetException) {
                        binding.csvBookers.setState(
                            ContentState.ERROR_NETWORK_GENERAL,
                            getString(R.string.no_internet_connection),
                        )
                    } else if (it.exception is UnAuthorizeException) {
                        errorHandler(it.exception)
                        binding.csvBookers.setState(
                            ContentState.ERROR_NETWORK_GENERAL,
                            getString(R.string.text_session_expired_please_login_again),
                        )
                    } else if (it.exception is ServerErrorException) {
                        errorHandler(it.exception)
                        binding.csvBookers.setState(
                            ContentState.ERROR_NETWORK_GENERAL,
                            getString(R.string.text_server_error_please_try_again_later),
                        )
                    } else {
                        Log.d("get-profile", "getProfileData: ${it.exception?.message}")
                    }
                },
                doOnLoading = {
                    binding.btnSave.isEnabled = false
                },
            )
        }
    }
}
