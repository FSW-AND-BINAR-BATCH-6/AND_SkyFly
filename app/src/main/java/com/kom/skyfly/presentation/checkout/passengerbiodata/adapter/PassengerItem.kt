package com.kom.skyfly.presentation.checkout.passengerbiodata.adapter

import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.data.model.passenger.PassengerData
import com.kom.skyfly.databinding.ItemFormPassengerBiodataBinding
import com.xwray.groupie.viewbinding.BindableItem

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class PassengerItem(
    private val passengerType: String,
    private val position: Int,
    private val onDateClick: (EditText) -> Unit,
    private val onValidUntilClick: (EditText) -> Unit,
) : BindableItem<ItemFormPassengerBiodataBinding>() {
    private lateinit var viewBinding: ItemFormPassengerBiodataBinding

    override fun bind(
        viewBinding: ItemFormPassengerBiodataBinding,
        position: Int,
    ) {
        this.viewBinding = viewBinding

        with(viewBinding) {
            tvPassengerType.text = passengerType

            ArrayAdapter.createFromResource(
                root.context,
                R.array.titles_array,
                android.R.layout.simple_spinner_item,
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerTitle.adapter = adapter
            }

            scHaveFamilyName.setOnCheckedChangeListener { _, isChecked ->
                tvFamilyName.isVisible = isChecked
                tilFamilyName.isVisible = isChecked
            }

            etDate.setOnClickListener { onDateClick(etDate) }
            etValidUntil.setOnClickListener { onValidUntilClick(etValidUntil) }
            ivCalendar.setOnClickListener { onDateClick(etDate) }
            ivCalendarValidUntil.setOnClickListener { onValidUntilClick(etValidUntil) }
        }
    }

    override fun getLayout(): Int = R.layout.item_form_passenger_biodata

    override fun initializeViewBinding(view: View): ItemFormPassengerBiodataBinding {
        return ItemFormPassengerBiodataBinding.bind(view)
    }

    fun getPassengerData(): PassengerData {
        return with(viewBinding) {
            PassengerData(
                title = spinnerTitle.selectedItem.toString(),
                fullName = etFullName.text.toString(),
                familyName = if (scHaveFamilyName.isChecked) etFamilyName.text.toString() else null,
                dateOfBirth = etDate.text.toString(),
                nationality = etCountry.text.toString(),
                idOrPassport = etPassport.text.toString(),
                idOrPassportValidUntil = etValidUntil.text.toString(),
            )
        }
    }
}
