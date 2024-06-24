package com.kom.skyfly.presentation.home.search.viewitems

import android.annotation.SuppressLint
import android.view.View
import com.kom.skyfly.R
import com.kom.skyfly.data.model.home.search.Airport
import com.kom.skyfly.databinding.LayoutItemAirportSearchBinding
import com.xwray.groupie.viewbinding.BindableItem

class Items(
    private val data: Airport,
    private val onItemClick: (item: Airport) -> Unit,
) : BindableItem<LayoutItemAirportSearchBinding>() {
    @SuppressLint("SetTextI18n")
    override fun bind(
        viewBinding: LayoutItemAirportSearchBinding,
        position: Int,
    ) {
        with(viewBinding) {
            tvFlightHomeRecentSearch.text = "${data.city} (${data.code})"
            viewBinding.root.setOnClickListener {
                onItemClick(data)
            }
        }
    }

    override fun getLayout(): Int = R.layout.layout_item_airport_search

    override fun initializeViewBinding(view: View): LayoutItemAirportSearchBinding {
        return LayoutItemAirportSearchBinding.bind(view)
    }
}
