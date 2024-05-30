package com.kom.skyfly.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kom.skyfly.R
import com.kom.skyfly.data.source.network.model.login.LoginRequest
import com.kom.skyfly.data.source.network.services.SkyFlyApiService
import com.kom.skyfly.databinding.ActivityMainBinding
import com.kom.skyfly.presentation.bottomsheetsdialog.BottomSheetsDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setBottomNavbar()
    }

    private fun setBottomNavbar() {
        val navController = findNavController(R.id.nav_host)
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when (destination.id) {
                R.id.menu_history_tab -> {
                    if (mainViewModel.getUserToken() == null) {
                        openNotLoggedInModal()
                        controller.popBackStack(R.id.menu_home_tab, false)
                    }
                }

                R.id.menu_account_tab -> {
                    if (mainViewModel.getUserToken() == null) {
                        openNotLoggedInModal()
                        controller.popBackStack(R.id.menu_home_tab, false)
                    }
                }

                R.id.menu_notification_tab -> {
                    if (mainViewModel.getUserToken() == null) {
                        openNotLoggedInModal()
                        controller.popBackStack(R.id.menu_home_tab, false)
                    }
                }
            }
        }
    }

    private fun openNotLoggedInModal() {
        val bottomSheetFragment = BottomSheetsDialogFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun getDataFromApi() {
        val apiService = SkyFlyApiService.invoke()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val loginRequest = LoginRequest("example@gmail.com", "example123")
                val response = apiService.login(loginRequest)
                Log.d("Coins", "Response: $response")
            } catch (e: Exception) {
                if (e is retrofit2.HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    Log.e(
                        "Coins Error",
                        "HTTP Error: ${e.code()} - ${e.message()}\nBody: $errorBody",
                        e,
                    )
                } else {
                    Log.e("Coins Error", "Error: ${e.message}", e)
                }
            }
        }
    }
}
