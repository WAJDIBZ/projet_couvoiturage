package com.example.projet_couvoiturage.ui.employee.trips

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_couvoiturage.data.local.entity.TripEntity
import com.example.projet_couvoiturage.databinding.ItemTripBinding

class TripAdapter(private val onClick: (TripEntity) -> Unit) :
    ListAdapter<TripEntity, TripAdapter.TripViewHolder>(TripDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TripViewHolder(
        private val binding: ItemTripBinding,
        private val onClick: (TripEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: TripEntity) {
            binding.tvRoute.text = "${trip.departureCity} -> ${trip.arrivalCity}"
            binding.tvDateTime.text = "${trip.date} at ${trip.departureTime}"
            binding.tvPrice.text = "${trip.pricePerSeat} TND"
            binding.tvSeats.text = "${trip.seatsAvailable} seats left"
            binding.root.setOnClickListener { onClick(trip) }
        }
    }

    class TripDiffCallback : DiffUtil.ItemCallback<TripEntity>() {
        override fun areItemsTheSame(oldItem: TripEntity, newItem: TripEntity): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: TripEntity, newItem: TripEntity): Boolean = oldItem == newItem
    }
}
