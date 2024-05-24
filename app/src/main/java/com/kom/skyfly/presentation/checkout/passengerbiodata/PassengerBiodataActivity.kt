package com.kom.skyfly.presentation.checkout.passengerbiodata

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityPassengerBiodataBinding
import java.util.Calendar

class PassengerBiodataActivity : AppCompatActivity() {
    private val binding: ActivityPassengerBiodataBinding by lazy {
        ActivityPassengerBiodataBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setHaveFamilyName()
        setSpinnerTitle()
        setClickListeners()
        setTitleHeader()
    }

    private fun setTitleHeader() {
        binding.layoutHeader.tvTitleHeader.text = getString(R.string.text_passenger_biodata_title)
    }

    private fun setClickListeners() {
        binding.layoutFormPassengerBiodata.ivCalendar.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog =
                DatePickerDialog(
                    this,
                    { view, year, monthOfYear, dayOfMonth ->
                        val dat = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                        binding.layoutFormPassengerBiodata.etDate.setText(dat)
                    },
                    year,
                    month,
                    day,
                )

            datePickerDialog.show()
        }
        binding.layoutFormPassengerBiodata.ivCalendarValidUntil.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog =
                DatePickerDialog(
                    this,
                    { view, year, monthOfYear, dayOfMonth ->
                        val dat = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                        binding.layoutFormPassengerBiodata.etValidUntil.setText(dat)
                    },
                    year,
                    month,
                    day,
                )

            datePickerDialog.show()
        }
    }

    private fun setSpinnerTitle() {
        val spinner: Spinner = binding.layoutFormPassengerBiodata.spinnerTitle
        ArrayAdapter.createFromResource(
            this,
            R.array.titles_array,
            android.R.layout.simple_spinner_item,
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun setHaveFamilyName() {
        with(binding.layoutFormPassengerBiodata) {
            scHaveFamilyName.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    tvFamilyName.isVisible = true
                    tilFamilyName.isVisible = true
                } else {
                    tvFamilyName.isVisible = false
                    tilFamilyName.isVisible = false
                }
            }
        }
    }
}
