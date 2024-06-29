package com.kom.skyfly.presentation.home.seatclass.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kom.skyfly.R
import com.kom.skyfly.data.model.home.seat_class.SeatClassHome
import com.kom.skyfly.databinding.LayoutItemSeatclassCardBinding

class SeatClassAdapter(private val itemClick: (SeatClassHome) -> Unit) :
    RecyclerView.Adapter<SeatClassAdapter.ItemSeatClassViewHolder>() {
    private var selectedItemPosition = RecyclerView.NO_POSITION

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<SeatClassHome>() {
                override fun areItemsTheSame(
                    oldItem: SeatClassHome,
                    newItem: SeatClassHome,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: SeatClassHome,
                    newItem: SeatClassHome,
                ): Boolean {
                    return oldItem == newItem
                }
            },
        )

    fun submitData(data: List<SeatClassHome>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemSeatClassViewHolder {
        val binding =
            LayoutItemSeatclassCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ItemSeatClassViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemSeatClassViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position], position == selectedItemPosition)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    inner class ItemSeatClassViewHolder(
        private val binding: LayoutItemSeatclassCardBinding,
        private val itemClick: (SeatClassHome) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(
            item: SeatClassHome,
            isSelected: Boolean,
        ) {
            with(item) {
                binding.tvSeatclassTitle.text = seatClassName
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
                    binding.ivChecklist.isVisible = true
                } else {
                    binding.ivChecklist.isVisible = false
                }
                binding.tvSeatclassTitle.setTextColor(titleTextColor)
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
