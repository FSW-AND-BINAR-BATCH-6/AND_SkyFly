package com.kom.skyfly.presentation.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentAccountBinding
import com.kom.skyfly.presentation.account.editprofile.BottomSheetsEditProfile
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding

    private val accountViewModel: AccountViewModel by viewModel()

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
    }

    private fun setClickListeners() {
        binding.layoutBtnProfile.tvEditProfile.setOnClickListener {
            doEditProfile()
        }
        binding.layoutBtnProfile.tvLogoutProfile.setOnClickListener {
            accountViewModel.doLogout(null)
            Toast.makeText(requireContext(), "Anda telah keluar!", Toast.LENGTH_SHORT).show()
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.menu_home_tab)
    }

    private fun doEditProfile() {
        val bottomSheetFragment = BottomSheetsEditProfile()
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }
}
