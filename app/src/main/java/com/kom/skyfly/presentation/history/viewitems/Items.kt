package com.kom.skyfly.presentation.history.viewitems

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import com.kom.skyfly.R
import com.kom.skyfly.data.model.history.new.TransactionDomain
import com.kom.skyfly.databinding.ItemOrderHistoryBinding
import com.kom.skyfly.databinding.ItemSectionHeaderHistoryBinding
import com.kom.skyfly.utils.formatToRupiah
import com.xwray.groupie.viewbinding.BindableItem

class HeaderItem(
    private val title: String,
    private val onHeaderClick: (item: String) -> Unit,
) : BindableItem<ItemSectionHeaderHistoryBinding>() {
    override fun bind(
        viewBinding: ItemSectionHeaderHistoryBinding,
        position: Int,
    ) {
        viewBinding.tvHistoryDate.text = title
        viewBinding.root.setOnClickListener { onHeaderClick.invoke(title) }
    }

    override fun getLayout(): Int = R.layout.item_section_header_history

    override fun initializeViewBinding(view: View): ItemSectionHeaderHistoryBinding {
        return ItemSectionHeaderHistoryBinding.bind(view)
    }
}

class DataItem(
    private val data: TransactionDomain,
    private val onItemClick: (item: TransactionDomain, transactionId: String?) -> Unit,
) :
    BindableItem<ItemOrderHistoryBinding>() {
    var paymentStatus: String? = null
    var departureCity: String? = null
    var departureDate: String? = null
    var departureTime: String? = null
    var flightDuration: String? = null
    var destinationCity: String? = null
    var arrivalDate: String? = null
    var arrivalTime: String? = null
    var bookingCode: String? = null
    var seatClass: String? = null
    var totalPrice: String? = null
    var transactionId: String? = null

    override fun bind(
        viewBinding: ItemOrderHistoryBinding,
        position: Int,
    ) {
        paymentStatus = data.status
        departureCity = data.transactionDetail.first().flight.departureAirport.city
        departureDate = data.transactionDetail.first().flight.departure.date
        departureTime = data.transactionDetail.first().flight.departure.time
        flightDuration = data.transactionDetail.first().flight.flightDuration
        destinationCity = data.transactionDetail.first().flight.destinationAirport.city
        arrivalDate = data.transactionDetail.first().flight.arrival.date
        arrivalTime = data.transactionDetail.first().flight.arrival.time
        bookingCode = data.booking.code
        seatClass = data.transactionDetail.first().seat.type
        totalPrice = data.totalPrice.formatToRupiah().toString()
        transactionId = data.id

        with(viewBinding) {
            tvStatus.text = paymentStatus
            tvDepartureLocation.text = departureCity
            tvDepartureDate.text = departureDate
            tvDepartureTime.text = departureTime
            tvFlightDuration.text = flightDuration
            tvDestination.text = destinationCity
            tvArrivalDate.text = arrivalDate
            tvArrivalTime.text = arrivalTime
            tvBookingCode.text = bookingCode
            tvClass.text = seatClass
            tvHistoryTotal.text = totalPrice

            viewBinding.root.setOnClickListener {
                onItemClick(data, transactionId)
            }

            if (paymentStatus.equals("pending", ignoreCase = true)) {
                tvStatus.setText(R.string.text_unpaid)
                tvStatus.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            tvStatus.context,
                            R.color.red,
                        ),
                    )
            } else if (paymentStatus.equals("settlement", ignoreCase = true)) {
                tvStatus.setText(R.string.text_paid)
                tvStatus.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            tvStatus.context,
                            R.color.green,
                        ),
                    )
            } else if (paymentStatus.equals("expire", ignoreCase = true) || paymentStatus.equals("cancel", ignoreCase = true)) {
                tvStatus.setText(R.string.text_cancelled)
                tvStatus.backgroundTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            tvStatus.context,
                            R.color.darkGrey,
                        ),
                    )
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_order_history

    override fun initializeViewBinding(view: View): ItemOrderHistoryBinding {
        return ItemOrderHistoryBinding.bind(view)
    }
}
