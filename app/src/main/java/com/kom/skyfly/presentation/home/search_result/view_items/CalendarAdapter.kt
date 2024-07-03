package com.kom.skyfly.presentation.home.search_result.view_items

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kom.skyfly.R
import com.kom.skyfly.data.model.home.Calendar
import com.kom.skyfly.databinding.LayoutDateBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalendarAdapter(private val itemClick: (Calendar) -> Unit) :
    RecyclerView.Adapter<CalendarAdapter.ItemCalendarViewHolder>() {
    private var selectedDate: String? = null

    class ItemCalendarViewHolder(
        private val binding: LayoutDateBinding,
        val itemClick: (Calendar) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindView(
            item: Calendar,
            isSelected: Boolean,
        ) {
            with(item) {
                binding.tvCalendarDay.text = day
                binding.tvCalendarDate.text = date
                val context = binding.root.context
                val currentDate = LocalDate.now()
                val itemDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                if (isSelected) {
                    binding.cvCalendar.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.md_theme_primaryFixed_mediumContrast,
                        ),
                    )
                    binding.tvCalendarDay.setTextColor(Color.WHITE)
                    binding.tvCalendarDate.setTextColor(Color.WHITE)
                } else {
                    binding.cvCalendar.setCardBackgroundColor(Color.WHITE)
                    binding.tvCalendarDay.setTextColor(Color.BLACK)
                    binding.tvCalendarDate.setTextColor(Color.BLACK)
                }

                if (itemDate.isBefore(currentDate)) {
                    binding.cvCalendar.alpha = 0.5f
                    itemView.setOnClickListener(null)
                } else {
                    binding.cvCalendar.alpha = 1.0f
                    itemView.setOnClickListener {
                        itemClick(this)
                    }
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

    fun submitData(
        data: List<Calendar>,
        initialSelectedDate: String? = null,
    ) {
        selectedDate = initialSelectedDate
        asyncDataDiffer.submitList(data)
    }

    fun findPositionOfDate(date: String): Int {
        return asyncDataDiffer.currentList.indexOfFirst { it.date == date }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemCalendarViewHolder {
        val binding = LayoutDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCalendarViewHolder(binding) { calendar ->
            setSelectedDate(calendar.date)
            itemClick(calendar)
        }
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ItemCalendarViewHolder,
        position: Int,
    ) {
        val item = asyncDataDiffer.currentList[position]
        val isSelected = item.date == selectedDate
        holder.bindView(item, isSelected)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedDate(date: String) {
        selectedDate = date
        notifyDataSetChanged()
    }
}
