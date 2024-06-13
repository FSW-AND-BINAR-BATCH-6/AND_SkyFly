package com.kom.skyfly.presentation.home.search.viewitems

import android.view.View
import com.kom.skyfly.R
import com.kom.skyfly.data.model.home.search.Airport
import com.kom.skyfly.databinding.LayoutItemRecentSearchBinding
import com.xwray.groupie.viewbinding.BindableItem

class Items(
    private val data: Airport,
    private val onItemClick: (item: Airport) -> Unit,
) : BindableItem<LayoutItemRecentSearchBinding>() {
    override fun bind(
        viewBinding: LayoutItemRecentSearchBinding,
        position: Int,
    ) {
        with(viewBinding) {
            tvFlightHomeRecentSearch.text = data.name
            viewBinding.root.setOnClickListener {
                onItemClick(data)
            }
        }
    }

    override fun getLayout(): Int = R.layout.layout_item_recent_search

    override fun initializeViewBinding(view: View): LayoutItemRecentSearchBinding {
        return LayoutItemRecentSearchBinding.bind(view)
    }
}
