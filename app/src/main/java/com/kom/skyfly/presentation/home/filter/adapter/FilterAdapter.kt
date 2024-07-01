package com.kom.skyfly.presentation.home.filter.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kom.skyfly.R
import com.kom.skyfly.data.model.home.filter.Filter
import com.kom.skyfly.databinding.LayoutItemFilterCardBinding

class FilterAdapter(private val itemClick: (Filter) -> Unit) :
    RecyclerView.Adapter<FilterAdapter.ItemFilterViewHolder>() {
    private var selectedItemPosition = RecyclerView.NO_POSITION

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Filter>() {
                override fun areItemsTheSame(
                    oldItem: Filter,
                    newItem: Filter,
                ): Boolean {
                    return oldItem.filterName == newItem.filterName
                }

                override fun areContentsTheSame(
                    oldItem: Filter,
                    newItem: Filter,
                ): Boolean {
                    return oldItem == newItem
                }
            },
        )

    fun submitData(data: List<Filter>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemFilterViewHolder {
        val binding =
            LayoutItemFilterCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ItemFilterViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemFilterViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position], position == selectedItemPosition)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    inner class ItemFilterViewHolder(
        private val binding: LayoutItemFilterCardBinding,
        private val itemClick: (Filter) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(
            item: Filter,
            isSelected: Boolean,
        ) {
            with(item) {
                binding.tvKindOfFilter.text = item.filterName
                val bgColor =
                    if (isSelected) {
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.md_theme_primaryFixed_mediumContrast,
                        )
                    } else {
                        ContextCompat.getColor(binding.root.context, android.R.color.white)
                    }
                binding.cvSeatClass.setCardBackgroundColor(bgColor)

                val titleTextColor =
                    if (isSelected) {
                        Color.WHITE
                    } else {
                        ContextCompat.getColor(binding.root.context, android.R.color.black)
                    }
                if (isSelected) {
                    binding.ivChecklistFilter.isVisible = true
                } else {
                    binding.ivChecklistFilter.isVisible = false
                }
                binding.tvFilterTitle.setTextColor(titleTextColor)
                itemView.setOnClickListener {
                    itemClick(item)
                    setSelected(adapterPosition)
                }
            }
        }

        private fun setSelected(position: Int) {
            if (selectedItemPosition != position) {
                val previousSelected = selectedItemPosition
                selectedItemPosition = position
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedItemPosition)
            }
        }
    }
}
