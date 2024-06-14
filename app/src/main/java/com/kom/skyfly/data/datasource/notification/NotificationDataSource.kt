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
                notificationTitle = "Dapatkan Potongan 50%",
                notificationContent = "Dapatkan potongan 50% dalam pembelian tiket! Promo ini berlaku untuk semua penerbangan domestik dan internasional. Jangan lewatkan kesempatan emas ini untuk menikmati perjalanan dengan harga yang lebih hemat.",
            ),
            Notification(
                type = "Peringatan",
                date = "30 Mei 2024, 15:00",
                notificationTitle = "Peringatan Penerbangan",
                notificationContent = "Jangan lewatkan penerbangan Anda. Check-in sekarang! Penerbangan Anda akan berangkat dalam waktu 3 jam. Pastikan Anda telah menyelesaikan proses check-in dan persiapan sebelum penerbangan.",
            ),
            Notification(
                type = "Informasi",
                date = "30 Mei 2024, 16:00",
                notificationTitle = "Pemberitahuan Penerbangan",
                notificationContent = "Penerbangan Anda ke Bali akan berangkat dalam 2 jam. Mohon tiba di gerbang keberangkatan setidaknya 45 menit sebelum jadwal berangkat untuk menghindari keterlambatan.",
            ),
            Notification(
                type = "Pembaruan",
                date = "30 Mei 2024, 17:00",
                notificationTitle = "Pembaruan Aplikasi",
                notificationContent = "Aplikasi kami telah diperbarui ke versi terbaru. Nikmati fitur baru sekarang! Pembaruan ini mencakup peningkatan kecepatan aplikasi, antarmuka yang lebih ramah pengguna, dan berbagai fitur tambahan untuk meningkatkan pengalaman Anda.",
            ),
            Notification(
                type = "Promosi",
                date = "31 Mei 2024, 09:00",
                notificationTitle = "Beli Satu Tiket, Gratis Satu Tiket",
                notificationContent = "Beli satu tiket, dapatkan satu tiket gratis hanya untuk hari ini! Penawaran ini berlaku untuk semua tujuan. Jangan lewatkan kesempatan ini untuk berbagi pengalaman terbang bersama teman atau keluarga.",
            ),
            Notification(
                type = "Peringatan",
                date = "31 Mei 2024, 10:00",
                notificationTitle = "Peringatan Baterai Rendah",
                notificationContent = "Baterai ponsel Anda rendah. Pastikan Anda mengisi daya sebelum perjalanan. Kami sarankan untuk membawa power bank atau pengisi daya tambahan untuk memastikan ponsel Anda tetap menyala selama perjalanan.",
            ),
            Notification(
                type = "Informasi",
                date = "31 Mei 2024, 11:00",
                notificationTitle = "Pemberitahuan Bandara",
                notificationContent = "Bandara akan ditutup sementara untuk pemeliharaan dari pukul 22:00 hingga 04:00. Harap sesuaikan jadwal perjalanan Anda dan pastikan Anda tiba di bandara sebelum waktu penutupan.",
            ),
            Notification(
                type = "Pembaruan",
                date = "31 Mei 2024, 12:00",
                notificationTitle = "Rute Baru Ditambahkan",
                notificationContent = "Rute baru telah ditambahkan ke tujuan populer Anda. Jelajahi sekarang! Kami telah menambahkan penerbangan langsung ke beberapa kota destinasi favorit Anda. Cek rute baru dan pesan tiket Anda hari ini.",
            ),
        )
    }
}
