package com.kom.skyfly.presentation.checkout.passengerbiodata

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityPassengerBiodataBinding
import com.kom.skyfly.presentation.checkout.passengerbiodata.adapter.PassengerAdapter
import java.util.Calendar

class PassengerBiodataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPassengerBiodataBinding
    private lateinit var passengerAdapter: PassengerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerBiodataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        setTitleHeader()
        handleNextButtonClick()
    }

    private fun setUpRecyclerView() {
        val adult = 1
        val children = 1
        val baby = 0
        binding.rvPassengerForm.layoutManager = LinearLayoutManager(this)
        passengerAdapter =
            PassengerAdapter(
                binding.rvPassengerForm,
                adult, children, baby,
            ) { position ->
            }
        binding.rvPassengerForm.adapter = passengerAdapter
    }

    private fun setTitleHeader() {
        binding.layoutHeader.tvTitleHeader.text = getString(R.string.text_passenger_biodata_title)
    }

    private fun showDatePickerDialog(editText: EditText) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog =
            DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val date = "$dayOfMonth/${monthOfYear + 1}/$year"
                    editText.setText(date)
                },
                year,
                month,
                day,
            )
        datePickerDialog.show()
    }

    private fun handleNextButtonClick() {
        binding.btnSave.setOnClickListener {
            val passengerDataList = passengerAdapter.getPassengerDataList()
            for (passengerData in passengerDataList) {
                Log.d("PassengerData", passengerData.toString())
            }
        }
    }
}
