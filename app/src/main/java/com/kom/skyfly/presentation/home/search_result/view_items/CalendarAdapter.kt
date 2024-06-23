package com.kom.skyfly.presentation.home.search_result.view_items

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kom.skyfly.data.model.home.Calendar
import com.kom.skyfly.databinding.LayoutDateBinding

class CalendarAdapter(private val itemClick: (Calendar) -> Unit) :
    RecyclerView.Adapter<CalendarAdapter.ItemCalendarViewHolder>() {
    class ItemCalendarViewHolder(
        private val binding: LayoutDateBinding,
        val itemClick: (Calendar) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindView(item: Calendar) {
            with(item) {
                binding.tvCalendarDay.text = day
                binding.tvCalendarDate.text = date

                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }

    private val asyncDataDiffer =
        AsyncListDiffer<Calendar>(
            this,
            object : DiffUtil.ItemCallback<Calendar>() {
                override fun areItemsTheSame(
                    oldItem: Calendar,
                    newItem: Calendar,
                ): Boolean {
                    return oldItem.date == newItem.date
                }

                override fun areContentsTheSame(
                    oldItem: Calendar,
                    newItem: Calendar,
                ): Boolean {
                    return oldItem == newItem
                }
            },
        )

    fun submitData(data: List<Calendar>) {
        asyncDataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemCalendarViewHolder {
        val binding = LayoutDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCalendarViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ItemCalendarViewHolder,
        position: Int,
    ) {
        holder.bindView(asyncDataDiffer.currentList[position])
    }
}
