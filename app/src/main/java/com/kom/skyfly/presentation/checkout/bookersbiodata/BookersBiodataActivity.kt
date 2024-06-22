package com.kom.skyfly.presentation.checkout.bookersbiodata

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.databinding.ActivityOrdererBiodataBinding
import com.kom.skyfly.presentation.checkout.passengerbiodata.PassengerBiodataActivity
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.utils.NoInternetException
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setHaveFamilyName()
        setTitleHeader()
        getProfileData()
        setActionListeners()
    }

    private fun navigateToPassengerBiodata() {
        val intent =
            Intent(this, PassengerBiodataActivity::class.java).apply {
                putExtra("EXTRAS_FULL_NAME", fullName)
                putExtra("EXTRAS_FAMILY_NAME", familyName)
                putExtra("EXTRAS_EMAIL", email)
                putExtra("EXTRAS_PHONE_NUMBER", phoneNumber)
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
                    it.payload.let { data ->
                        id = data?.userId
                        email = data?.email
                        familyName = data?.familyName
                        fullName = data?.fullName
                        phoneNumber = data?.phoneNumber
                        binding.layoutFormCustomerBiodata.etFullName.setText(data?.fullName)
                        binding.layoutFormCustomerBiodata.etEmail.setText(data?.email)
                        binding.layoutFormCustomerBiodata.etNoTlp.setText(data?.phoneNumber)
                        binding.layoutFormCustomerBiodata.etFamilyName.setText(data?.familyName)
                        binding.main.isRefreshing = false
                    }
                },
                doOnError = {
                    binding.main.isRefreshing = false
                    if (it.exception is NoInternetException) {
                        binding.csvBookers.setState(
                            ContentState.ERROR_NETWORK_GENERAL,
                            "No internet connection!!",
                        )
                    }
                    val errorMessage = it.exception?.message
                    if (errorMessage != null && errorMessage.contains("jwt expired")) {
                        handleUnAuthorize()
                    } else {
                        Log.d("get-profile", "getProfileData: ${it.exception?.message}")
                    }
                },
            )
        }
    }
}
