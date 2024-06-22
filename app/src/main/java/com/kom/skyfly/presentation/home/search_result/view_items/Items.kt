package com.kom.skyfly.presentation.home.search_result.view_items

import android.annotation.SuppressLint
import android.view.View
import com.kom.skyfly.R
import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.databinding.LayoutItemCardSearchResultBinding
import com.xwray.groupie.viewbinding.BindableItem

class Items(
    private val data: FlightTicket,
    private val onItemClick: (item: FlightTicket) -> Unit,
) : BindableItem<LayoutItemCardSearchResultBinding>() {
    @SuppressLint("SetTextI18n")
    override fun bind(
        viewBinding: LayoutItemCardSearchResultBinding,
        position: Int,
    ) {
        with(viewBinding) {
            tvTimeDepartureSearchResult.text = data.departureTime
            tvTimeArrivalSearchResult.text = data.arrivalTime
            tvTargetDepartureSearchResult.text = data.departureCountryCode
            tvTargetArrivalSearchResult.text = data.arrivalCountryCode
            tvFlightDurationSearchResult.text = data.duration
            tvTransitOptionSearchResult.text = "Direct"
            tvPriceSearchResult.text = data.price.toString()
            tvAirlineSearchResult.text = "${data.airplaneName} - ${data.seatClass}"
            viewBinding.root.setOnClickListener {
                onItemClick(data)
            }
        }
    }

    override fun getLayout(): Int = R.layout.layout_item_card_search_result

    override fun initializeViewBinding(view: View): LayoutItemCardSearchResultBinding {
        return LayoutItemCardSearchResultBinding.bind(view)
    }
}
