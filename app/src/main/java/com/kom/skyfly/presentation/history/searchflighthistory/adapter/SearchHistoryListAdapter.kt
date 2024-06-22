package com.kom.skyfly.presentation.history.searchflighthistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kom.skyfly.data.model.searchhistory.SearchHistory
import com.kom.skyfly.databinding.ItemFlightNumberBinding

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class SearchHistoryListAdapter(
    private val itemClick: (
        SearchHistory,
    ) -> Unit,
    private val searchHistoryListener: SearchHistoryListener? = null,
) : RecyclerView.Adapter<SearchHistoryListAdapter.SearchHistoryViewHolder>() {
    class SearchHistoryViewHolder(
        private val binding: ItemFlightNumberBinding,
        val itemClick: (SearchHistory) -> Unit,
        private val searchHistoryListener: SearchHistoryListener?,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: SearchHistory) {
            binding.tvFlightNumber.text = item.searchHistory
            itemView.setOnClickListener { itemClick(item) }
            setClickListeners(item)
        }

        private fun setClickListeners(item: SearchHistory) {
            with(binding) {
                ivCloseList.setOnClickListener { searchHistoryListener?.onDeleteItemClicked(item) }
            }
        }
    }

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<SearchHistory>() {
                override fun areItemsTheSame(
                    oldItem: SearchHistory,
                    newItem: SearchHistory,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: SearchHistory,
                    newItem: SearchHistory,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<SearchHistory>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchHistoryViewHolder {
        val binding =
            ItemFlightNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHistoryViewHolder(binding, itemClick, searchHistoryListener)
    }

    override fun onBindViewHolder(
        holder: SearchHistoryViewHolder,
        position: Int,
    ) {
        (holder.bindView(dataDiffer.currentList[position]))
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size
}

interface SearchHistoryListener {
    fun onDeleteItemClicked(searchHistory: SearchHistory)
}
