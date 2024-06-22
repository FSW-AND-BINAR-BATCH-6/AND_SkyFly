package com.kom.skyfly.presentation.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kom.skyfly.R
import com.kom.skyfly.data.model.destinationfavorite.DestinationFavorite
import com.kom.skyfly.databinding.LayoutItemTicketGridBinding

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class DestinationFavoriteAdapter(private val itemClick: (DestinationFavorite) -> Unit) :
    RecyclerView.Adapter<DestinationFavoriteAdapter.ItemDestinationFavoriteViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<DestinationFavorite>() {
                override fun areItemsTheSame(
                    oldItem: DestinationFavorite,
                    newItem: DestinationFavorite,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: DestinationFavorite,
                    newItem: DestinationFavorite,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<DestinationFavorite>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemDestinationFavoriteViewHolder {
        val binding =
            LayoutItemTicketGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemDestinationFavoriteViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemDestinationFavoriteViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemDestinationFavoriteViewHolder(
        private val binding: LayoutItemTicketGridBinding,
        val itemClick: (DestinationFavorite) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(item: DestinationFavorite) {
            with(item) {
                binding.ivTicketImage.load(item.imageUrl) {
                    crossfade(true)
                    error(R.mipmap.ic_launcher)
                }
                val isLimited = item.isLimited
                val discount = item.discount
                if (isLimited == true) {
                    binding.tvLimited.isVisible = true
                    binding.tvLimited.text = "Limited"
                } else if (discount != null) {
                    binding.tvLimited.isVisible = true
                    binding.tvLimited.text = discount
                } else {
                    binding.tvLimited.isVisible = false
                }
                binding.tvCardStartFrom.text = item.origin
                binding.tvCardDestination.text = "${item.origin} -> ${item.destination}"
                binding.tvCardDate.text = item.dateRange
                binding.tvCardAirplane.text = item.airline
                binding.tvCardPrice.text = item.price
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
