package com.kom.skyfly.presentation.home.detail_home.view_item

import android.annotation.SuppressLint
import android.view.View
import com.kom.skyfly.R
import com.kom.skyfly.data.model.home.flight.FlightTicket
import com.kom.skyfly.databinding.LayoutItemCardTicketDetailHomeBinding
import com.xwray.groupie.viewbinding.BindableItem

class Items(
    private val data: FlightTicket,
    private val onItemClick: (item: FlightTicket) -> Unit,
) : BindableItem<LayoutItemCardTicketDetailHomeBinding>() {
    @SuppressLint("SetTextI18n")
    override fun bind(
        viewBinding: LayoutItemCardTicketDetailHomeBinding,
        position: Int,
    ) {
        with(viewBinding) {
            tvFlightDestinationDetailTicket.text =
                "${data.departureCity} - > ${data.arrivalCity} (${data.duration})"
            tvDepartureTimeDetailTicket.text = data.departureTime
            tvDepartureDateDetailTicket.text = data.departureDate
            tvDepartureAirportDetailTicket.text = "${data.departureAirport} - ${data.departureTerminal}"
            tvAirplaneNameDetailTicket.text = data.airplaneName
            tvSeatClassDetailTicket.text = data.seatClass
            tvAirplaneCodeDetailTicket.text = data.code
            tvFacilitiesDetailTicket.text = data.facilities
            tvArrivalTimeDetailTicket.text = data.arrivalTime
            tvArrivalDateDetailTicket.text = data.arrivalDate
            tvDestinationAirportDetailTicket.text = data.arrivalAirport
        }
    }

    override fun getLayout(): Int = R.layout.layout_item_card_ticket_detail_home

    override fun initializeViewBinding(view: View): LayoutItemCardTicketDetailHomeBinding {
        return LayoutItemCardTicketDetailHomeBinding.bind(view)
    }
}
