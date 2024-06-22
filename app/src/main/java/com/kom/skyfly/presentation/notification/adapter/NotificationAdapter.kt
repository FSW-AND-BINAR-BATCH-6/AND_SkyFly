package com.kom.skyfly.presentation.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kom.skyfly.data.model.notification.Notification
import com.kom.skyfly.databinding.ItemNotificationBinding

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class NotificationAdapter(private val itemClick: (Notification) -> Unit) :
    RecyclerView.Adapter<NotificationAdapter.ItemNotificationViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Notification>() {
                override fun areItemsTheSame(
                    oldItem: Notification,
                    newItem: Notification,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Notification,
                    newItem: Notification,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Notification>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemNotificationViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemNotificationViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: ItemNotificationViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    class ItemNotificationViewHolder(
        private val binding: ItemNotificationBinding,
        val itemClick: (Notification) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Notification) {
            with(item) {
                binding.tvNotificationType.text = item.type
                binding.tvNotificationContent.text = item.notificationsTitle
                binding.tvNotificationDate.text = item.date
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
