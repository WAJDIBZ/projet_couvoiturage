package com.example.projet_couvoiturage.ui.traject

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_couvoiturage.R
import com.example.projet_couvoiturage.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrajectDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traject_details)

        val tripId = intent.getLongExtra("tripId", -1)
        if (tripId == -1L) {
            finish()
            return
        }

        val route = findViewById<TextView>(R.id.tv_route)
        val dateTime = findViewById<TextView>(R.id.tv_date_time)
        val depCoords = findViewById<TextView>(R.id.tv_departure_coords)
        val arrCoords = findViewById<TextView>(R.id.tv_arrival_coords)
        val seats = findViewById<TextView>(R.id.tv_seats)
        val notes = findViewById<TextView>(R.id.tv_notes)
        val passengerEmpty = findViewById<TextView>(R.id.tv_passenger_empty)
        val passengerList = findViewById<RecyclerView>(R.id.recycler_passengers)

        val adapter = PassengerAdapter()
        passengerList.layoutManager = LinearLayoutManager(this)
        passengerList.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.get(this@TrajectDetailsActivity)
            val trip = db.tripDao().getTripByIdOnce(tripId) ?: return@launch
            val passengers = db.reservationDao().passengersForTrip(tripId)

            withContext(Dispatchers.Main) {
                route.text = "${trip.departureCity} -> ${trip.arrivalCity}"
                dateTime.text = "${trip.date} at ${trip.departureTime}"
                depCoords.text = "Departure: ${trip.departureLat}, ${trip.departureLng}"
                arrCoords.text = "Arrival: ${trip.arrivalLat}, ${trip.arrivalLng}"
                seats.text = "Seats: ${trip.seatsAvailable}/${trip.seatsTotal}"
                notes.text = "Notes: ${trip.notes}"

                adapter.submitList(passengers)
                passengerEmpty.visibility = if (passengers.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
            }
        }
    }
}
