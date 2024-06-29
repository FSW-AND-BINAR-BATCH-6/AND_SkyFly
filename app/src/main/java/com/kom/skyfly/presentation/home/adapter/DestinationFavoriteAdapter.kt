package com.kom.skyfly.presentation.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kom.skyfly.R
import com.kom.skyfly.data.model.home.destination_favourite.DestinationFavourite
import com.kom.skyfly.databinding.LayoutItemTicketGridBinding
import com.kom.skyfly.utils.formatToRupiah

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class DestinationFavoriteAdapter(private val itemClick: (DestinationFavourite) -> Unit) :
    RecyclerView.Adapter<DestinationFavoriteAdapter.ItemDestinationFavoriteViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<DestinationFavourite>() {
                override fun areItemsTheSame(
                    oldItem: DestinationFavourite,
                    newItem: DestinationFavourite,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: DestinationFavourite,
                    newItem: DestinationFavourite,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<DestinationFavourite>) {
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
        val itemClick: (DestinationFavourite) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(item: DestinationFavourite) {
            with(item) {
                binding.ivTicketImage.load(item.img) {
                    crossfade(true)
                    error(R.mipmap.ic_launcher)
                }
                binding.tvCardDestination.text = "${item.departureCity} -> ${item.arrivalCity}"
                binding.tvCardAirplane.text = item.airline
                binding.tvCardPrice.text = item.price.formatToRupiah()
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
