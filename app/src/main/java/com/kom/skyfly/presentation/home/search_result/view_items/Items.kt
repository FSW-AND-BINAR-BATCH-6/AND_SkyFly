package com.kom.skyfly.presentation.home.search_result.view_items

import android.annotation.SuppressLint
import android.view.View
import com.kom.skyfly.R
import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.databinding.LayoutItemCardSearchResultBinding
import com.kom.skyfly.utils.formatToRupiah
import com.xwray.groupie.viewbinding.BindableItem

class Items(
    private val data: FlightTicket?,
    private val onItemClick: (item: FlightTicket?) -> Unit,
) : BindableItem<LayoutItemCardSearchResultBinding>() {
    @SuppressLint("SetTextI18n")
    override fun bind(
        viewBinding: LayoutItemCardSearchResultBinding,
        position: Int,
    ) {
        with(viewBinding) {
//            ivIcAirlineSearchResult.load(data?.airplaneImg) {
//                crossfade(true)
//                error(R.mipmap.ic_launcher)
//            }
            tvTimeDepartureSearchResult.text = data?.departureTime
            tvTimeArrivalSearchResult.text = data?.arrivalTime
            tvTargetDepartureSearchResult.text = data?.departureCountryCode
            tvTargetArrivalSearchResult.text = data?.arrivalCountryCode
            tvFlightDurationSearchResult.text = data?.duration
            if(data?.directNotes == false){
                tvTransitOptionSearchResult.text = "Direct"
            }else{
                tvTransitOptionSearchResult.text = "Transit"
            }
            tvPriceSearchResult.text = data?.price.formatToRupiah().toString()
            tvAirlineSearchResult.text = "${data?.airplaneName} - ${data?.seatClass}"
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
