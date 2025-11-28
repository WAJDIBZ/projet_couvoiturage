package com.example.projet_couvoiturage.ui.admin.alerts

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_couvoiturage.data.local.entity.AlertEntity
import com.example.projet_couvoiturage.databinding.ItemAlertBinding

class AlertAdapter(private val onClick: (AlertEntity) -> Unit) :
    ListAdapter<AlertEntity, AlertAdapter.AlertViewHolder>(AlertDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val binding = ItemAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlertViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AlertViewHolder(
        private val binding: ItemAlertBinding,
        private val onClick: (AlertEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alert: AlertEntity) {
            binding.tvAlertType.text = alert.type
            binding.tvAlertMessage.text = alert.message
            if (!alert.isRead) {
                binding.tvAlertMessage.setTypeface(null, Typeface.BOLD)
            } else {
                binding.tvAlertMessage.setTypeface(null, Typeface.NORMAL)
            }
            binding.root.setOnClickListener { onClick(alert) }
        }
    }

    class AlertDiffCallback : DiffUtil.ItemCallback<AlertEntity>() {
        override fun areItemsTheSame(oldItem: AlertEntity, newItem: AlertEntity): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: AlertEntity, newItem: AlertEntity): Boolean = oldItem == newItem
    }
}
