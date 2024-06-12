package com.kom.skyfly.presentation.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.databinding.FragmentAccountBinding
import com.kom.skyfly.presentation.account.editprofile.BottomSheetsEditProfile
import com.kom.skyfly.utils.proceedWhen
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding

    private val accountViewModel: AccountViewModel by viewModel()

    private var email: String? = null
    private var fullName: String? = null
    private var phoneNumber: String? = null
    private var id: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        getProfileData()
    }

    private fun confirmReqChangePassword() {
        val dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.text_message_confirm_change_password))
                .setPositiveButton(
                    getString(R.string.text_yes),
                ) { dialog, id ->
                    email?.let { reqChangePassword(it) }
                }
                .setNegativeButton(
                    getString(R.string.text_no),
                ) { dialog, id ->
                }.create()
        dialog.show()
    }

    private fun reqChangePassword(email: String) {
        accountViewModel.forgetPassword(email).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    Toasty.success(
                        requireContext(),
                        getString(R.string.text_req_change_password),
                        Toast.LENGTH_SHORT,
                        true,
                    ).show()
                },
                doOnError = {
                    Log.d("req-changePassword", "reqChangePassword: ${it.exception?.message}")
                },
            )
        }
    }

    private fun setClickListeners() {
        binding.layoutBtnProfile.tvEditProfile.setOnClickListener {
            fullName?.let { fullName ->
                phoneNumber?.let { phoneNumber ->
                    id?.let { id ->
                        doEditProfile(id, fullName, phoneNumber)
                    }
                }
            }
        }
        binding.layoutBtnProfile.tvLogoutProfile.setOnClickListener {
            accountViewModel.doLogout(null)
            (activity as BaseActivity).handleUnAuthorize()
            Toasty.normal(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
            navigateToHome()
        }
        binding.layoutBtnProfile.tvChangePassword.setOnClickListener {
            confirmReqChangePassword()
        }
    }

    private fun getProfileData() {
        accountViewModel.getProfile().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    it.payload.let { data ->
                        id = data?.userId
                        email = data?.email
                        fullName = data?.fullName
                        phoneNumber = data?.phoneNumber
                        binding.layoutProfileUser.etEmail.setText(data?.email)
                        binding.layoutProfileUser.etFullName.setText(data?.fullName)
                        binding.layoutProfileUser.etPhoneNumber.setText(data?.phoneNumber)
                    }
                },
                doOnError = {
                    Log.d("get-profile", "getProfileData: ${it.exception?.message}")
                },
            )
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.menu_home_tab)
    }

    private fun doEditProfile(
        id: String,
        fullName: String,
        phoneNumber: String,
    ) {
        val bottomSheetFragment = BottomSheetsEditProfile.newInstance(id, fullName, phoneNumber)
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }
}
