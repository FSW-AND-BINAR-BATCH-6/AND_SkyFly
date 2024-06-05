package com.kom.skyfly.presentation.history.viewitems

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import com.kom.skyfly.R
import com.kom.skyfly.data.model.history.Data
import com.kom.skyfly.databinding.ItemOrderHistoryBinding
import com.kom.skyfly.databinding.ItemSectionHeaderHistoryBinding
import com.xwray.groupie.viewbinding.BindableItem

class HeaderItem(private val title: String, private val onHeaderClick: (item: String) -> Unit) :
    BindableItem<ItemSectionHeaderHistoryBinding>() {
    override fun bind(
        viewBinding: ItemSectionHeaderHistoryBinding,
        position: Int,
    ) {
        viewBinding.tvHistoryDate.text = title
        viewBinding.root.setOnClickListener { onHeaderClick.invoke(title) }
    }

    override fun getLayout(): Int = R.layout.item_section_header_history

    override fun initializeViewBinding(view: View): ItemSectionHeaderHistoryBinding = ItemSectionHeaderHistoryBinding.bind(view)
}

class DataItem(private val data: Data, private val onItemClick: (item: Data) -> Unit) :
    BindableItem<ItemOrderHistoryBinding>() {
    override fun bind(
        viewBinding: ItemOrderHistoryBinding,
        position: Int,
    ) {
        with(viewBinding) {
            tvStatus.text = data.status
            tvDepartureLocation.text = data.departureLocation
            tvDepartureDate.text = data.departureDate
            tvDepartureTime.text = data.departureTime
            tvFlightDuration.text = data.flightDuration
            tvDestination.text = data.destination
            tvArrivalDate.text = data.arrivalDate
            tvArrivalTime.text = data.arrivalTime
            tvBookingCode.text = data.bookingCode
            tvClass.text = data.flightClass
            tvHistoryTotal.text = data.total

            viewBinding.root.setOnClickListener {
                onItemClick(data)
            }

            when (data.status) {
                "Unpaid" -> {
                    tvStatus.background =
                        ContextCompat.getDrawable(tvStatus.context, R.drawable.btn_rounded)
                    tvStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                tvStatus.context,
                                R.color.red,
                            ),
                        )
                }

                "Cancelled" -> {
                    tvStatus.background =
                        ContextCompat.getDrawable(tvStatus.context, R.drawable.btn_rounded)
                    tvStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                tvStatus.context,
                                R.color.darkGrey,
                            ),
                        )
                }

                "Paid" -> {
                    tvStatus.background =
                        ContextCompat.getDrawable(tvStatus.context, R.drawable.btn_rounded)
                    tvStatus.backgroundTintList =
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                tvStatus.context,
                                R.color.green,
                            ),
                        )
                }
                else -> {
                    tvStatus.background = null
                }
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_order_history

    override fun initializeViewBinding(view: View): ItemOrderHistoryBinding {
        return ItemOrderHistoryBinding.bind(view)
    }
}
