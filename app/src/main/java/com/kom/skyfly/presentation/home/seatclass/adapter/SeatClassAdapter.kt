package com.kom.skyfly.presentation.home.seatclass.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kom.skyfly.data.model.home.seat_class.SeatClassHome
import com.kom.skyfly.databinding.LayoutItemSeatclassCardBinding

class SeatClassAdapter(private val itemClick: (SeatClassHome) -> Unit) :
    RecyclerView.Adapter<SeatClassAdapter.ItemSeatClassViewHolder>() {
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
                    return oldItem.hashCode() == newItem.hashCode()
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
            LayoutItemSeatclassCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemSeatClassViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemSeatClassViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemSeatClassViewHolder(
        private val binding: LayoutItemSeatclassCardBinding,
        val itemClick: (SeatClassHome) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: SeatClassHome) {
            with(item) {
                binding.tvSeatclassTitle.text = item.seatClassName
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
