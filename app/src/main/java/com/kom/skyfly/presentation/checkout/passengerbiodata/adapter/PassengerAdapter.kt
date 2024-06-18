package com.kom.skyfly.presentation.checkout.passengerbiodata.adapter

import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.kom.skyfly.R
import com.kom.skyfly.data.model.passenger.PassengerData
import com.kom.skyfly.databinding.ItemFormPassengerBiodataBinding
import java.util.Calendar

class PassengerAdapter(
    private val recyclerView: RecyclerView,
    private val adultsCount: Int,
    private val childrenCount: Int,
    private val infantCount: Int,
    private val itemClick: (Int) -> Unit,
) : RecyclerView.Adapter<PassengerAdapter.PassengerViewHolder>() {
    private val passengerDataList = mutableListOf<PassengerData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PassengerViewHolder {
        val binding =
            ItemFormPassengerBiodataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return PassengerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PassengerViewHolder,
        position: Int,
    ) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return adultsCount + childrenCount + infantCount
    }

    inner class PassengerViewHolder(
        private val binding: ItemFormPassengerBiodataBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            ArrayAdapter.createFromResource(
                itemView.context,
                R.array.titles_array,
                android.R.layout.simple_spinner_item,
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerTitle.adapter = adapter
            }

            binding.scHaveFamilyName.setOnCheckedChangeListener { _, isChecked ->
                binding.tvFamilyName.isVisible = isChecked
                binding.tilFamilyName.isVisible = isChecked
            }
            binding.etDate.setOnClickListener {
                val position = adapterPosition
                showDatePickerDialog(binding.etDate)
            }
            binding.etValidUntil.setOnClickListener {
                val position = adapterPosition
                showDatePickerDialog(binding.etValidUntil)
            }

            binding.ivCalendar.setOnClickListener {
                val position = adapterPosition
                showDatePickerDialog(binding.etDate)
            }
            binding.ivCalendarValidUntil.setOnClickListener {
                val position = adapterPosition
                showDatePickerDialog(binding.etValidUntil)
            }
        }

        private fun showDatePickerDialog(editText: EditText) {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog =
                DatePickerDialog(
                    itemView.context,
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

        fun bindView(passengerNumber: Int) {
            itemView.apply {
                val passengerIndex = passengerNumber + 1
                when {
                    passengerNumber < adultsCount -> {
                        binding.tvPassengerType.text = "Passenger $passengerIndex - Adult"
                    }

                    passengerNumber < adultsCount + childrenCount -> {
                        binding.tvPassengerType.text = "Passenger $passengerIndex - Children"
                    }

                    else -> {
                        binding.tvPassengerType.text = "Passenger $passengerIndex - Baby"
                    }
                }
                binding.etFullName.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        savePassengerData(adapterPosition)
                    }
                }
                binding.etDate.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        savePassengerData(adapterPosition)
                    }
                }
            }
        }
    }

    fun saveAllPassengerData() {
        for (i in 0 until itemCount) {
            savePassengerData(i)
        }
    }

    private fun savePassengerData(position: Int) {
        val itemView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView
        val spinnerTitle = itemView?.findViewById<Spinner>(R.id.spinner_title)
        val etFullName = itemView?.findViewById<EditText>(R.id.et_full_name)
        val etFamilyName = itemView?.findViewById<EditText>(R.id.et_family_name)
        val etDateOfBirth = itemView?.findViewById<EditText>(R.id.et_date)
        val nationality = itemView?.findViewById<EditText>(R.id.et_country)
        val idOrPassport = itemView?.findViewById<EditText>(R.id.et_passport)
        val etValidUntil = itemView?.findViewById<EditText>(R.id.et_valid_until)
        if (spinnerTitle != null && etFullName != null && etDateOfBirth != null && nationality != null && idOrPassport != null && etValidUntil != null) {
            val passengerData =
                PassengerData(
                    spinnerTitle.selectedItem.toString(),
                    etFullName.text.toString(),
                    etFamilyName?.text.toString(),
                    etDateOfBirth.text.toString(),
                    nationality.text.toString(),
                    idOrPassport.text.toString(),
                    etValidUntil.text.toString(),
                )
            if (position < passengerDataList.size) {
                passengerDataList[position] = passengerData
            } else {
                passengerDataList.add(passengerData)
            }
        }
    }

    fun getPassengerDataList(): List<PassengerData> {
        saveAllPassengerData()
        return passengerDataList
    }
}
