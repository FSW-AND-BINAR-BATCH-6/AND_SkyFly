package com.kom.skyfly.presentation.account.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentBottomSheetsEditProfileBinding
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetsEditProfile : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetsEditProfileBinding

    private val bottomSheetsEditProfileViewModel: BottomSheetsEditProfileViewModel by viewModel()

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
            confirmUpdateProfile()
        }
    }

    private fun doUpdateProfile() {
        val name = binding.etFullName.text.toString().trim()
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()

        id?.let {
            bottomSheetsEditProfileViewModel.updateProfile(it, name, phoneNumber)
                .observe(viewLifecycleOwner) { result ->
                    result.proceedWhen(
                        doOnSuccess = {
                        },
                    )
                }
        }
    }

    private fun setEditTextData() {
    }

    private fun confirmUpdateProfile() {
        val dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.text_confirm_update_profile))
                .setPositiveButton(
                    getString(R.string.text_oke),
                ) { dialog, id ->
                    doUpdateProfile()

                }
                .create()
        dialog.show()
    }
}
