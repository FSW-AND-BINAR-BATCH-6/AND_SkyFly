package com.kom.skyfly.presentation.account.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.databinding.FragmentBottomSheetsEditProfileBinding

class BottomSheetsEditProfile : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetsEditProfileBinding

    companion object {
        private const val ARG_FULL_NAME = "arg_full_name"
        private const val ARG_PHONE_NUMBER = "arg_phone_number"

        fun newInstance(
            fullName: String?,
            phoneNumber: String?,
        ): BottomSheetsEditProfile {
            val fragment = BottomSheetsEditProfile()
            val args = Bundle()
            args.putString(ARG_FULL_NAME, fullName)
            args.putString(ARG_PHONE_NUMBER, phoneNumber)
            fragment.arguments = args
            return fragment
        }
    }

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
        val fullName = arguments?.getString(ARG_FULL_NAME)
        val phoneNumber = arguments?.getString(ARG_PHONE_NUMBER)

        binding.etFullName.setText(fullName)
        binding.etPhoneNumber.setText(phoneNumber)
    }
}
