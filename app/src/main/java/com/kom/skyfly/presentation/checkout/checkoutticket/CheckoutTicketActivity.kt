package com.kom.skyfly.presentation.checkout.checkoutticket

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityCheckoutTicketBinding
import com.kom.skyfly.databinding.ActivityFlightDetailBinding
import com.kom.skyfly.presentation.checkout.payment.PaymentActivity

class CheckoutTicketActivity : AppCompatActivity() {
    private val binding: ActivityCheckoutTicketBinding by lazy {
        ActivityCheckoutTicketBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitleHeader()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnProceedToPayment.setOnClickListener {
            val paymentUrl = "https://www.youtube.com/" // Ganti dengan URL pembayaran yang sebenarnya
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("payment_url", paymentUrl)
            startActivity(intent)
        }
    }

    private fun setTitleHeader() {
        binding.layoutHeader.tvTitleHeader.text = getString(R.string.text_header_detail_history)
    }
}
