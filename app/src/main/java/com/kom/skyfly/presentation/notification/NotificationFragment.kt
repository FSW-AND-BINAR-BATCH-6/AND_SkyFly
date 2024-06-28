package com.kom.skyfly.presentation.notification

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.notification.Notification
import com.kom.skyfly.databinding.FragmentNotificationBinding
import com.kom.skyfly.presentation.common.views.ContentState
import com.kom.skyfly.presentation.notification.adapter.NotificationAdapter
import com.kom.skyfly.presentation.notificationdetail.NotificationDetailActivity
import com.kom.skyfly.utils.NoInternetException
import com.kom.skyfly.utils.ServerErrorException
import com.kom.skyfly.utils.UnAuthorizeException
import com.kom.skyfly.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val notificationViewModel: NotificationViewModel by viewModel()

    private val notificationAdapter: NotificationAdapter by lazy {
        NotificationAdapter {
            val intent = Intent(requireContext(), NotificationDetailActivity::class.java)
            intent.putExtra("EXTRA_NOTIFICATION", it)
            startActivity(intent)
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
        setActionListeners()
        setupNotificationList()
        getNotificationData()
    }

    private fun setActionListeners() {
        binding.srlNotification.setOnRefreshListener {
            getNotificationData()
        }
    }

    private fun getNotificationData() {
        notificationViewModel.getAllNotification().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.srlNotification.isRefreshing = false
                        binding.shmProgressNotification.isVisible = false
                        binding.csvNotification.setState(ContentState.SUCCESS)
                        binding.rvNotification.isVisible = true
                        it.payload?.let { data -> bindNotificationList(data) }
                    }, 1000)
                },
                doOnLoading = {
                    binding.srlNotification.isRefreshing = false
                    binding.rvNotification.isVisible = false
                    binding.shmProgressNotification.isVisible = true
                },
                doOnEmpty = {
                    binding.srlNotification.isRefreshing = false
                    binding.csvNotification.setState(ContentState.EMPTY)
                    binding.shmProgressNotification.isVisible = false
                },
                doOnError = { error ->
                    binding.srlNotification.isRefreshing = false
                    binding.shmProgressNotification.isVisible = false

                    if (error.exception is NoInternetException) {
                        binding.csvNotification.setState(
                            ContentState.ERROR_NETWORK,
                            getString(R.string.no_internet_connection),
                        )
                    } else if (error.exception is UnAuthorizeException) {
                        (activity as BaseActivity).errorHandler(error.exception)
                        binding.csvNotification.setState(
                            ContentState.ERROR_NETWORK_GENERAL,
                            getString(R.string.text_session_expired_please_login_again),
                        )
                    } else if (error.exception is ServerErrorException) {
                        (activity as BaseActivity).errorHandler(error.exception)
                        binding.csvNotification.setState(
                            ContentState.ERROR_NETWORK,
                            getString(R.string.text_server_error_please_try_again_later),
                            R.drawable.img_empty_data,
                        )
                    } else {
                        binding.csvNotification.setState(ContentState.ERROR_GENERAL)
                    }
                    Log.e("NotificationFragment", "Error: ${error.exception?.message}")
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
