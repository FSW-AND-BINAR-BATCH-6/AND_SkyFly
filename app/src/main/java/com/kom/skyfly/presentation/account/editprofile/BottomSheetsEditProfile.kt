package com.kom.skyfly.presentation.account.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentBottomSheetsEditProfileBinding
import com.kom.skyfly.utils.proceedWhen
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetsEditProfile : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetsEditProfileBinding

    private val sharedViewModelEditProfile: SharedViewModelEditProfile by viewModel()

    companion object {
        private const val ARG_FULL_NAME = "arg_full_name"
        private const val ARG_PHONE_NUMBER = "arg_phone_number"
        private const val ARG_USER_ID = "arg_user_id"

        fun newInstance(
            id: String?,
            fullName: String?,
            phoneNumber: String?,
        ): BottomSheetsEditProfile {
            val fragment = BottomSheetsEditProfile()
            val args = Bundle()
            args.putString(ARG_FULL_NAME, fullName)
            args.putString(ARG_PHONE_NUMBER, phoneNumber)
            args.putString(ARG_USER_ID, id)
            fragment.arguments = args
            return fragment
        }
    }

    var id: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBottomSheetsEditProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setEditTextData()
        setClickListeners()
        id = arguments?.getString(ARG_USER_ID)
        val fullName = arguments?.getString(ARG_FULL_NAME)
        val phoneNumber = arguments?.getString(ARG_PHONE_NUMBER)
        binding.etFullName.setText(fullName)
        binding.etPhoneNumber.setText(phoneNumber)
    }

    private fun setClickListeners() {
        binding.btnSubmit.setOnClickListener {
            if (isFormValid()) {
                confirmUpdateProfile()
            }
        }
    }

    private fun doEditProfile() {
        if (isFormValid()) {
            val phoneNumber = binding.etPhoneNumber.text.toString().trim()
            proceedEditProfile(phoneNumber)
        }
    }

    private fun proceedEditProfile(phoneNumber: String) {
        val name = binding.etFullName.text.toString().trim()
        sharedViewModelEditProfile.updateProfile(name, phoneNumber, null, null)
            .observe(viewLifecycleOwner) { result ->
                result.proceedWhen(
                    doOnSuccess = {
                        binding.btnSubmit.isVisible = true
                        binding.pbLoading.isVisible = false
                        Toasty.success(
                            requireContext(),
                            getString(R.string.text_data_updated_successfully),
                            Toast.LENGTH_SHORT,
                        ).show()
                        dismiss()
                    },
                    doOnError = {
                        binding.btnSubmit.isVisible = true
                        binding.pbLoading.isVisible = false
                        Toasty.error(
                            requireContext(),
                            getString(R.string.text_something_went_wrong),
                            Toast.LENGTH_SHORT,
                        ).show()
                    },
                    doOnLoading = {
                        binding.btnSubmit.isEnabled = false
                        binding.pbLoading.isVisible = true
                    },
                )
            }
    }

    private fun setEditTextData() {
    }

    private fun confirmUpdateProfile() {
        val dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.text_confirm_update_profile))
                .setPositiveButton(
                    getString(R.string.text_yes),
                ) { dialog, id ->
                    doEditProfile()
                }
                .setNegativeButton(
                    getString(R.string.text_no),
                ) { dialog, id ->
                }
                .create()
        dialog.show()
    }

    private fun phoneNumberValidation(phoneNumber: String): Boolean {
        return if (phoneNumber.isEmpty()) {
            binding.tilPhoneNumber.isErrorEnabled = true
            binding.tilPhoneNumber.error = getString(R.string.text_no_tlp_cannot_empty)
            false
        } else if (phoneNumber.length < 11 || phoneNumber.length > 13) {
            binding.tilPhoneNumber.isErrorEnabled = true
            binding.tilPhoneNumber.error = "Phone number must be >11 and <13 digits"
            false
        } else if (!phoneNumber.startsWith("62")) {
            binding.tilPhoneNumber.isErrorEnabled = true
            binding.tilPhoneNumber.endIconMode = TextInputLayout.END_ICON_NONE
            binding.tilPhoneNumber.error = getString(R.string.text_no_tlp_must_start_with_62)
            false
        } else {
            binding.tilPhoneNumber.isErrorEnabled = false
            true
        }
    }

    private fun isFormValid(): Boolean {
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        return phoneNumberValidation(phoneNumber)
    }
}
