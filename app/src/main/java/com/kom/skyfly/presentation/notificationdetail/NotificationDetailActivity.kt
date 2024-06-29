package com.kom.skyfly.presentation.notificationdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kom.skyfly.R
import com.kom.skyfly.data.model.notification.Notification
import com.kom.skyfly.databinding.ActivityNotificationDetailBinding
import es.dmoral.toasty.Toasty

class NotificationDetailActivity : AppCompatActivity() {
    private val binding: ActivityNotificationDetailBinding by lazy {
        ActivityNotificationDetailBinding.inflate(layoutInflater)
    }
    var notification: Notification? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        notification = intent.getParcelableExtra("EXTRA_NOTIFICATION")

        notification?.let { displayNotificationDetails(it) }

        setOnClickListeners()
    }

    @SuppressLint("ResourceType")
    private fun deleteNotification(it: Notification) {
        it.id?.let { it1 -> Toasty.error(this, it1, Toast.LENGTH_SHORT).show() }
    }

    private fun setOnClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun displayNotificationDetails(notification: Notification) {
        binding.tvNotificationTitles.text = notification.notificationsTitle
        binding.tvNotificationContent.text = notification.notificationsContent
        binding.tvNotificationTimes.text = notification.date
        binding.tvNotificationTypes.text = notification.type

        when (notification.type) {
            "Promotion" -> {
                binding.ivNotificationType.setImageResource(R.drawable.ic_promotion)
            }
            "Promotions" -> {
                binding.ivNotificationType.setImageResource(R.drawable.ic_promotion)
            }
            "Warning" -> {
                binding.ivNotificationType.setImageResource(R.drawable.ic_warning)
            }

            "Update" -> {
                binding.ivNotificationType.setImageResource(R.drawable.ic_update)
            }
            else -> {
                binding.ivNotificationType.setImageResource(R.drawable.ic_message)
            }
        }
    }
}
