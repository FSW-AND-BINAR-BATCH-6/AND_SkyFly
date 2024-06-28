package com.kom.skyfly.core

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kom.skyfly.R
import com.kom.skyfly.presentation.bottomsheetsdialog.BottomSheetsDialogFragment
import com.kom.skyfly.presentation.login.LoginActivity
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
open class BaseActivity : AppCompatActivity() {
    private val baseViewModel: BaseViewModel by viewModel()

    fun errorHandler(e: Exception) {
        if (e is UnAuthorizeException) {
            baseViewModel.clearSession()
            openNotLoggedInModal()
        } else if (e is ServerErrorException) {
            Toasty.error(
                this,
                getString(R.string.text_server_error_please_try_again_later),
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    fun doLogoutHandler() {
        baseViewModel.clearSession()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun openNotLoggedInModal() {
        if (!supportFragmentManager.isStateSaved) {
            val bottomSheetFragment = BottomSheetsDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}
