package com.kom.skyfly.presentation.home.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kom.skyfly.data.model.home.search_history.SearchDestinationHistory
import com.kom.skyfly.databinding.LayoutItemAirportRecentSearchBinding

class SearchDestinationHistoryAdapter(
    private val itemClick: (
        SearchDestinationHistory,
    ) -> Unit,
    private val searchDestinationHistoryListener: SearchDestinationHistoryListener? = null,
) : RecyclerView.Adapter<SearchDestinationHistoryAdapter.SearchDestinationHistoryViewHolder>() {
    class SearchDestinationHistoryViewHolder(
        private val binding: LayoutItemAirportRecentSearchBinding,
        val itemClick: (SearchDestinationHistory) -> Unit,
        private val searchDestinationHistoryListener: SearchDestinationHistoryListener?,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: SearchDestinationHistory) {
            binding.tvFlightHomeRecentSearch.text = item.searchDestinationHistory
            itemView.setOnClickListener { itemClick(item) }
            setClickListeners(item)
        }

        private fun setClickListeners(item: SearchDestinationHistory) {
            with(binding) {
                ivDeleteItem.setOnClickListener { searchDestinationHistoryListener?.onDeleteItemClicked(item) }
            }
        }
    }

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<SearchDestinationHistory>() {
                override fun areItemsTheSame(
                    oldItem: SearchDestinationHistory,
                    newItem: SearchDestinationHistory,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: SearchDestinationHistory,
                    newItem: SearchDestinationHistory,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<SearchDestinationHistory>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchDestinationHistoryViewHolder {
        val binding =
            LayoutItemAirportRecentSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchDestinationHistoryViewHolder(binding, itemClick, searchDestinationHistoryListener)
    }

    override fun onBindViewHolder(
        holder: SearchDestinationHistoryViewHolder,
        position: Int,
    ) {
        (holder.bindView(dataDiffer.currentList[position]))
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size
}

interface SearchDestinationHistoryListener {
    fun onDeleteItemClicked(searchDestinationHistory: SearchDestinationHistory)
}
