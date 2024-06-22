package com.kom.skyfly.presentation.checkout.passengerbiodata

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.kom.skyfly.R
import com.kom.skyfly.core.BaseActivity
import com.kom.skyfly.data.model.passenger.PassengerData
import com.kom.skyfly.databinding.ActivityPassengerBiodataBinding
import com.kom.skyfly.presentation.checkout.chooseseat.ChooseSeatActivity
import com.kom.skyfly.presentation.checkout.passengerbiodata.adapter.PassengerItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class PassengerBiodataActivity : BaseActivity() {
    private lateinit var binding: ActivityPassengerBiodataBinding
    private var passengerDataList: List<PassengerData> = emptyList()

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val passengerBiodataViewModel: PassengerBiodataViewModel by viewModel()

    private var email: String? = null
    private var fullNames: String? = null
    private var familyName: String? = null
    private var phoneNumber: String? = null
    private var adult = 0
    private var children = 0
    private var baby = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerBiodataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullNames = intent.getStringExtra("EXTRAS_FULL_NAME")
        familyName = intent.getStringExtra("EXTRAS_FAMILY_NAME")
        email = intent.getStringExtra("EXTRAS_EMAIL")
        phoneNumber = intent.getStringExtra("EXTRAS_PHONE_NUMBER")
        adult = intent.getIntExtra("EXTRAS_ADULT", 0)
        children = intent.getIntExtra("EXTRAS_CHILD", 0)
        baby = intent.getIntExtra("EXTRAS_BABY", 0)
        setUpRecyclerView()
        setTitleHeader()
        handleNextButtonClick()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.layoutHeader.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpRecyclerView() {
        adult = 1
        children = 0
        baby = 0

        binding.rvPassengerForm.layoutManager = LinearLayoutManager(this)
        binding.rvPassengerForm.adapter = groupAdapter

        for (i in 0 until (adult + children + baby)) {
            val passengerType =
                when {
                    i < adult -> "Passenger ${i + 1} - Adult"
                    i < adult + children -> "Passenger ${i + 1} - Children"
                    else -> "Passenger ${i + 1} - Baby"
                }
            val passengerTypeLabel =
                when {
                    i < adult -> "ADULT"
                    i < adult + children -> "CHILD"
                    else -> "INFRANT"
                }
            groupAdapter.add(
                PassengerItem(
                    passengerType,
                    passengerTypeLabel,
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
                    val date = "$year-${monthOfYear + 1}-$dayOfMonth"
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
            val mutableList = mutableListOf<PassengerData>()
            for (i in 0 until groupAdapter.itemCount) {
                val passengerItem = groupAdapter.getItem(i) as PassengerItem
                mutableList.add(passengerItem.getPassengerData())
            }
            passengerDataList = mutableList.toList()

            for (passengerData in passengerDataList) {
                Log.d("PassengerData", passengerData.toString())
            }

            val intent = Intent(this, ChooseSeatActivity::class.java)
            intent.putParcelableArrayListExtra("EXTRAS_PASSENGERS", ArrayList(passengerDataList))
            intent.putExtra("EXTRAS_FULL_NAME", fullNames)
            intent.putExtra("EXTRAS_FAMILY_NAME", familyName)
            intent.putExtra("EXTRAS_EMAIL", email)
            intent.putExtra("EXTRAS_PHONE_NUMBER", phoneNumber)
            intent.putExtra("EXTRAS_ADULT", adult)
            intent.putExtra("EXTRAS_CHILD", children)
            intent.putExtra("EXTRAS_BABY", baby)
            startActivity(intent)
        }
    }
}
