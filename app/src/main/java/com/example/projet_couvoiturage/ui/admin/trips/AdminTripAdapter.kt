package com.example.projet_couvoiturage.ui.admin.trips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_couvoiturage.data.local.entity.TripEntity
import com.example.projet_couvoiturage.databinding.ItemAdminTripBinding

class AdminTripAdapter(private val onCancelClick: (TripEntity) -> Unit) :
    ListAdapter<TripEntity, AdminTripAdapter.AdminTripViewHolder>(TripDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminTripViewHolder {
        val binding = ItemAdminTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminTripViewHolder(binding, onCancelClick)
    }

    override fun onBindViewHolder(holder: AdminTripViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AdminTripViewHolder(
        private val binding: ItemAdminTripBinding,
        private val onCancelClick: (TripEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: TripEntity) {
            binding.tvRoute.text = "${trip.departureCity} -> ${trip.arrivalCity}"
            binding.tvDate.text = trip.date
            binding.btnCancel.setOnClickListener { onCancelClick(trip) }
        }
    }

    class TripDiffCallback : DiffUtil.ItemCallback<TripEntity>() {
        override fun areItemsTheSame(oldItem: TripEntity, newItem: TripEntity): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: TripEntity, newItem: TripEntity): Boolean = oldItem == newItem
    }
}
