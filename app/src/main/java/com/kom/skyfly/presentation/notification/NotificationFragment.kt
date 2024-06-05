package com.kom.skyfly.presentation.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.kom.skyfly.data.model.notification.Notification
import com.kom.skyfly.databinding.FragmentNotificationBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.presentation.notification.adapter.NotificationAdapter
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val notificationViewModel: NotificationViewModel by viewModel()

    private val notificationAdapter: NotificationAdapter by lazy {
        NotificationAdapter {
            Toast.makeText(requireContext(), it.id, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupNotificationList()
        getNotificationData()
    }

    private fun getNotificationData() {
        notificationViewModel.getAllNotification().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.shmProgressNotification.isVisible = false
                    binding.csvNotification.setState(ContentState.SUCCESS)
                    binding.rvNotification.isVisible = true
                    it.payload?.let { data -> bindNotificationList(data) }
                },
                doOnLoading = {
                    binding.shmProgressNotification.isVisible = true
                },
                doOnEmpty = {
                    binding.csvNotification.setState(ContentState.EMPTY)
                    binding.shmProgressNotification.isVisible = false
                },
                doOnError = {
                    if (it.exception is NoInternetException) {
                        binding.csvNotification.setState(ContentState.ERROR_NETWORK)
                    } else {
                        binding.csvNotification.setState(ContentState.ERROR_GENERAL)
                    }
                    binding.shmProgressNotification.isVisible = false
                },
            )
        }
    }

    private fun bindNotificationList(data: List<Notification>) {
        notificationAdapter.submitData(data)
    }

    private fun setupNotificationList() {
        binding.rvNotification.apply {
            adapter = notificationAdapter
        }
    }
}
