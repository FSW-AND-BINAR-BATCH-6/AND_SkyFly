package com.kom.skyfly.presentation.checkout.passengerbiodata

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.passenger.PassengerData
import com.kom.skyfly.databinding.ActivityPassengerBiodataBinding
import com.kom.skyfly.presentation.checkout.passengerbiodata.adapter.PassengerItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.util.Calendar

class PassengerBiodataActivity : BaseActivity() {
    private lateinit var binding: ActivityPassengerBiodataBinding
    private val passengerDataList = mutableListOf<PassengerData>()

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

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
        val baby = 1

        binding.rvPassengerForm.layoutManager = LinearLayoutManager(this)
        binding.rvPassengerForm.adapter = groupAdapter

        for (i in 0 until (adult + children + baby)) {
            val passengerType =
                when {
                    i < adult -> "Passenger ${i + 1} - Adult"
                    i < adult + children -> "Passenger ${i + 1} - Children"
                    else -> "Passenger ${i + 1} - Baby"
                }
            groupAdapter.add(
                PassengerItem(
                    passengerType,
                    i,
                    this::showDatePickerDialog,
                    this::showDatePickerDialog,
                ),
            )
        }
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
            passengerDataList.clear()
            for (i in 0 until groupAdapter.itemCount) {
                val passengerItem = groupAdapter.getItem(i) as PassengerItem
                passengerDataList.add(passengerItem.getPassengerData())
            }

            for (passengerData in passengerDataList) {
                Log.d("PassengerData", passengerData.toString())
            }
        }
    }
}
