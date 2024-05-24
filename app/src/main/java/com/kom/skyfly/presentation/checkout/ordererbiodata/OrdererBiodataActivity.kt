package com.kom.skyfly.presentation.checkout.ordererbiodata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityOrdererBiodataBinding

class OrdererBiodataActivity : AppCompatActivity() {
    private val binding: ActivityOrdererBiodataBinding by lazy {
        ActivityOrdererBiodataBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setHaveFamilyName()
        setTitleHeader()
    }

    private fun setTitleHeader() {
        binding.layoutHeader.tvTitleHeader.text = getString(R.string.text_customer_biodata_title)
    }

    private fun setHaveFamilyName() {
        with(binding.layoutFormCustomerBiodata) {
            scHaveFamilyName.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    tvFamilyName.isVisible = true
                    tvFamilyName.isVisible = true
                    tvNoTlpTitle.isVisible = true
                    tilNoTlp.isVisible = true
                    tvEmailTitle.isVisible = true
                    tilEmail.isVisible = true
                } else {
                    tvFamilyName.isVisible = false
                    tvFamilyName.isVisible = false
                    tvNoTlpTitle.isVisible = false
                    tilNoTlp.isVisible = false
                    tvEmailTitle.isVisible = false
                    tilEmail.isVisible = false
                }
            }
        }
    }
}
