package com.example.projet_couvoiturage.ui.traject

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projet_couvoiturage.R
import com.example.projet_couvoiturage.auth.SessionManager
import com.example.projet_couvoiturage.data.local.AppDatabase
import com.example.projet_couvoiturage.data.local.entity.TripEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTrajectActivity : AppCompatActivity() {

    private val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val sdfTime = SimpleDateFormat("HH:mm", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_traject)

        val origin = findViewById<EditText>(R.id.input_origin)
        val dest   = findViewById<EditText>(R.id.input_destination)
        val dateEt = findViewById<EditText>(R.id.input_date)
        val timeEt = findViewById<EditText>(R.id.input_time)
        val seats  = findViewById<EditText>(R.id.input_seats)
        val notes  = findViewById<EditText>(R.id.input_notes)
        val save   = findViewById<Button>(R.id.btn_save)

        val cal = Calendar.getInstance()

        dateEt.setOnClickListener {
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, { _, yy, mm, dd ->
                cal.set(yy, mm, dd)
                dateEt.setText(sdfDate.format(cal.time))
            }, y, m, d).show()
        }

        timeEt.setOnClickListener {
            val h = cal.get(Calendar.HOUR_OF_DAY)
            val min = cal.get(Calendar.MINUTE)
            TimePickerDialog(this, { _, hh, mm ->
                cal.set(Calendar.HOUR_OF_DAY, hh)
                cal.set(Calendar.MINUTE, mm)
                timeEt.setText(sdfTime.format(cal.time))
            }, h, min, true).show()
        }

        save.setOnClickListener {
            val email = SessionManager.currentEmail
            if (email == null) {
                Toast.makeText(this, "Please log in first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val o = origin.text.toString().trim()
            val de = dest.text.toString().trim()
            val dt = dateEt.text.toString().trim()
            val tm = timeEt.text.toString().trim()
            val seatsVal = seats.text.toString().trim().toIntOrNull()
            val n = notes.text?.toString()

            if (o.isEmpty() || de.isEmpty() || dt.isEmpty() || tm.isEmpty() || seatsVal == null) {
                Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = AppDatabase.get(this)
            CoroutineScope(Dispatchers.IO).launch {
                val user = db.userDao().getUserByEmail(email)
                val driverId = user?.id ?: 0L

                val trip = TripEntity(
                    driverId = driverId,
                    departureCity = o,
                    departureLat = 0.0,
                    departureLng = 0.0,
                    arrivalCity = de,
                    arrivalLat = 0.0,
                    arrivalLng = 0.0,
                    date = dt,
                    departureTime = tm,
                    seatsTotal = seatsVal,
                    seatsAvailable = seatsVal,
                    pricePerSeat = 0.0,
                    notes = n ?: ""
                )
                db.tripDao().insertTrip(trip)
            }

            Toast.makeText(this, "Traject added", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
