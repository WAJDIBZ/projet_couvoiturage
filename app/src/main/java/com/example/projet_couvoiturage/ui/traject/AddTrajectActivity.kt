package com.example.projet_couvoiturage.ui.traject

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.projet_couvoiturage.R
import com.example.projet_couvoiturage.auth.SessionManager
import com.example.projet_couvoiturage.data.local.AppDatabase
import com.example.projet_couvoiturage.data.local.entity.PlaceEntity
import com.example.projet_couvoiturage.data.local.entity.TripEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTrajectActivity : AppCompatActivity() {

    private val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_traject)

        val sourceField = findViewById<AutoCompleteTextView>(R.id.drop_source)
        val destinationField = findViewById<AutoCompleteTextView>(R.id.drop_destination)
        val dateEt = findViewById<EditText>(R.id.input_date)
        val timeEt = findViewById<EditText>(R.id.input_time)
        val seats = findViewById<EditText>(R.id.input_seats)
        val notes = findViewById<EditText>(R.id.input_notes)
        val save = findViewById<Button>(R.id.btn_save)

        val cal = Calendar.getInstance()
        val db = AppDatabase.get(this)

        var selectedSource: PlaceEntity? = null
        var selectedDestination: PlaceEntity? = null

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
                timeEt.setText(String.format(Locale.US, "%02d:%02d", hh, mm))
            }, h, min, true).show()
        }

        lifecycleScope.launch {
            val places = withContext(Dispatchers.IO) { db.placeDao().getAll() }
            if (places.isEmpty()) {
                Toast.makeText(this@AddTrajectActivity, "No places available", Toast.LENGTH_SHORT).show()
                return@launch
            }
            val names = places.map { it.name }
            val adapter = ArrayAdapter(this@AddTrajectActivity, android.R.layout.simple_list_item_1, names)
            sourceField.setAdapter(adapter)
            destinationField.setAdapter(adapter)

            sourceField.setOnItemClickListener { _, _, position, _ ->
                selectedSource = places.getOrNull(position)
            }
            destinationField.setOnItemClickListener { _, _, position, _ ->
                selectedDestination = places.getOrNull(position)
            }
        }

        save.setOnClickListener {
            val email = SessionManager.currentEmail
            if (email == null) {
                Toast.makeText(this, "Please log in first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dt = dateEt.text.toString().trim()
            val tm = timeEt.text.toString().trim()
            val seatsVal = seats.text.toString().trim().toIntOrNull()
            val notesText = notes.text?.toString() ?: ""
            val src = selectedSource
            val dst = selectedDestination

            if (src == null || dst == null || dt.isEmpty() || tm.isEmpty() || seatsVal == null) {
                Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val user = db.userDao().getUserByEmail(email)
                val driverId = user?.id
                if (driverId == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddTrajectActivity, "No user record for $email", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val trip = TripEntity(
                    driverId = driverId,
                    departureCity = src.name,
                    departureLat = src.lat,
                    departureLng = src.lng,
                    arrivalCity = dst.name,
                    arrivalLat = dst.lat,
                    arrivalLng = dst.lng,
                    date = dt,
                    departureTime = tm,
                    seatsTotal = seatsVal,
                    seatsAvailable = seatsVal,
                    pricePerSeat = 0.0,
                    notes = notesText
                )
                db.tripDao().insertTrip(trip)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddTrajectActivity, "Traject added", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
