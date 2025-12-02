package com.example.projet_couvoiturage.ui.traject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_couvoiturage.R
import com.example.projet_couvoiturage.data.local.entity.TripEntity

class TripAdapter(private val onClick: (TripEntity) -> Unit) :
    ListAdapter<TripEntity, TripAdapter.TripViewHolder>(TripDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trip_compact, parent, false)
        return TripViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TripViewHolder(
        private val container: View,
        private val onClick: (TripEntity) -> Unit
    ) : RecyclerView.ViewHolder(container) {

        private val route: TextView = container.findViewById(R.id.tv_route)
        private val dateTime: TextView = container.findViewById(R.id.tv_date_time)
        private val seats: TextView = container.findViewById(R.id.tv_seats)

        fun bind(trip: TripEntity) {
            route.text = "${trip.departureCity} -> ${trip.arrivalCity}"
            dateTime.text = "${trip.date} at ${trip.departureTime}"
            seats.text = "${trip.seatsAvailable}/${trip.seatsTotal} seats"
            container.setOnClickListener { onClick(trip) }
        }
    }

    class TripDiffCallback : DiffUtil.ItemCallback<TripEntity>() {
        override fun areItemsTheSame(oldItem: TripEntity, newItem: TripEntity): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: TripEntity, newItem: TripEntity): Boolean = oldItem == newItem
    }
}
