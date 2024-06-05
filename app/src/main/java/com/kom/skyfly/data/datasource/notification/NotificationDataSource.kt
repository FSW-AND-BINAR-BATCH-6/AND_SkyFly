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
                type = "Promosi",
                date = "30 Mei 2024, 14:50",
                notification = "Dapatkan potongan 50% dalam pembelian tiket!",
            ),
            Notification(
                type = "Peringatan",
                date = "30 Mei 2024, 15:00",
                notification = "Jangan lewatkan penerbangan Anda. Check-in sekarang!",
            ),
            Notification(
                type = "Informasi",
                date = "30 Mei 2024, 16:00",
                notification = "Penerbangan Anda ke Bali akan berangkat dalam 2 jam.",
            ),
            Notification(
                type = "Pembaruan",
                date = "30 Mei 2024, 17:00",
                notification = "Aplikasi kami telah diperbarui ke versi terbaru. Nikmati fitur baru sekarang!",
            ),
            Notification(
                type = "Promosi",
                date = "31 Mei 2024, 09:00",
                notification = "Beli satu tiket, dapatkan satu tiket gratis hanya untuk hari ini!",
            ),
            Notification(
                type = "Peringatan",
                date = "31 Mei 2024, 10:00",
                notification = "Baterai ponsel Anda rendah. Pastikan Anda mengisi daya sebelum perjalanan.",
            ),
            Notification(
                type = "Informasi",
                date = "31 Mei 2024, 11:00",
                notification = "Bandara akan ditutup sementara untuk pemeliharaan dari pukul 22:00 hingga 04:00.",
            ),
            Notification(
                type = "Pembaruan",
                date = "31 Mei 2024, 12:00",
                notification = "Rute baru telah ditambahkan ke tujuan populer Anda. Jelajahi sekarang!",
            ),
        )
    }
}
