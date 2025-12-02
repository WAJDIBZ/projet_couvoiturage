package com.example.projet_couvoiturage.ui.traject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_couvoiturage.R
import com.example.projet_couvoiturage.data.local.entity.UserEntity

class PassengerAdapter : RecyclerView.Adapter<PassengerAdapter.PassengerViewHolder>() {

    private val items = mutableListOf<UserEntity>()

    fun submitList(newItems: List<UserEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_passenger, parent, false)
        return PassengerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class PassengerViewHolder(private val container: View) : RecyclerView.ViewHolder(container) {
        private val name: TextView = container.findViewById(R.id.tv_passenger_name)
        private val email: TextView = container.findViewById(R.id.tv_passenger_email)

        fun bind(user: UserEntity) {
            name.text = user.fullName
            email.text = user.email
        }
    }
}
