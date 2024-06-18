package com.kom.skyfly.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.databinding.ActivityMainBinding
import com.kom.skyfly.presentation.bottomsheetsdialog.BottomSheetsDialogFragment
import com.kom.skyfly.presentation.checkout.passengerbiodata.PassengerBiodataActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainViewModel: MainViewModel by viewModel()

    private val userToken = MutableLiveData<String?>()

    fun setUserToken(token: String?) {
        userToken.value = token
    }

    fun getUserToken(): String? {
        return userToken.value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setBottomNavbar()
//        navigate()
    }

    private fun setBottomNavbar() {
        val navController = findNavController(R.id.nav_host)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when (destination.id) {
                R.id.menu_history_tab, R.id.menu_account_tab, R.id.menu_notification_tab -> {
                    if (mainViewModel.getUserToken().isNullOrEmpty()) {
                        controller.popBackStack(R.id.menu_home_tab, false)
                        openNotLoggedInModal()
                    }
                }
            }
        }
    }

    private fun openNotLoggedInModal() {
        if (!supportFragmentManager.isStateSaved) {
            val bottomSheetFragment = BottomSheetsDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun navigate() {
        startActivity(
            Intent(this, PassengerBiodataActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }
}
