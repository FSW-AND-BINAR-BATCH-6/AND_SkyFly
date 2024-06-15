package com.kom.skyfly.data.datasource.notification

import com.kom.skyfly.data.model.notification.Notification

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
interface NotificationDataSource {
    fun getAllNotification(): List<Notification>
}

class NotificationDataSourceImpl() : NotificationDataSource {
    override fun getAllNotification(): List<Notification> {
        return mutableListOf(
            Notification(
                type = "Promotion",
                date = "May 30, 2024, 14:50",
                notificationTitle = "Get 50% Discount",
                notificationContent = "Get a 50% discount on ticket purchases! This promotion applies to all domestic and international flights. Don't miss this golden opportunity to enjoy travel at a more affordable price.",
            ),
            Notification(
                type = "Warning",
                date = "May 30, 2024, 15:00",
                notificationTitle = "Flight Reminder",
                notificationContent = "Don't miss your flight. Check-in now! Your flight will depart in 3 hours. Make sure you have completed the check-in process and preparations before the flight.",
            ),
            Notification(
                type = "Information",
                date = "May 30, 2024, 16:00",
                notificationTitle = "Flight Notification",
                notificationContent = "Your flight to Bali will depart in 2 hours. Please arrive at the departure gate at least 45 minutes before the scheduled departure time to avoid delays.",
            ),
            Notification(
                type = "Update",
                date = "May 30, 2024, 17:00",
                notificationTitle = "App Update",
                notificationContent = "Our app has been updated to the latest version. Enjoy the new features now! This update includes improved app speed, a more user-friendly interface, and additional features to enhance your experience.",
            ),
            Notification(
                type = "Promotion",
                date = "May 31, 2024, 09:00",
                notificationTitle = "Buy One Ticket, Get One Free",
                notificationContent = "Buy one ticket, get one free only for today! This offer applies to all destinations. Don't miss this chance to share the flying experience with a friend or family member.",
            ),
            Notification(
                type = "Warning",
                date = "May 31, 2024, 10:00",
                notificationTitle = "Low Battery Warning",
                notificationContent = "Your phone battery is low. Make sure to charge it before the trip. We recommend bringing a power bank or extra charger to ensure your phone stays powered on during the journey.",
            ),
            Notification(
                type = "Information",
                date = "May 31, 2024, 11:00",
                notificationTitle = "Airport Notice",
                notificationContent = "The airport will be temporarily closed for maintenance from 22:00 to 04:00. Please adjust your travel schedule and make sure to arrive at the airport before the closing time.",
            ),
            Notification(
                type = "Update",
                date = "May 31, 2024, 12:00",
                notificationTitle = "New Routes Added",
                notificationContent = "New routes have been added to your popular destinations. Explore now! We have added direct flights to several of your favorite destination cities. Check out the new routes and book your tickets today.",
            ),
        )
    }
}
