package com.example.projet_couvoiturage.ui.traject

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projet_couvoiturage.R
import com.example.projet_couvoiturage.data.AppDatabase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddTrajectActivity : AppCompatActivity() {

    private val conducteurEmail = "driver@example.com"

    private val viewModel: AddTrajectViewModel by viewModels {
        AddTrajectViewModel.provideFactory(AppDatabase.get(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_traject)

        val inputOrigin = findViewById<TextInputEditText>(R.id.input_origin)
        val inputDestination = findViewById<TextInputEditText>(R.id.input_destination)
        val inputDateTime = findViewById<TextInputEditText>(R.id.input_datetime)
        val inputSeats = findViewById<TextInputEditText>(R.id.input_seats)
        val inputNotes = findViewById<TextInputEditText>(R.id.input_notes)
        val btnSave = findViewById<MaterialButton>(R.id.btn_save)

        btnSave.setOnClickListener {
            val origin = inputOrigin.text?.toString()?.trim().orEmpty()
            val dest = inputDestination.text?.toString()?.trim().orEmpty()
            val dtStr = inputDateTime.text?.toString()?.trim().orEmpty()
            val seatsStr = inputSeats.text?.toString()?.trim().orEmpty()
            val notes = inputNotes.text?.toString()

            if (origin.isEmpty() || dest.isEmpty() || dtStr.isEmpty() || seatsStr.isEmpty()) {
                Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dt = dtStr.toLongOrNull()
            val seats = seatsStr.toIntOrNull()
            if (dt == null || seats == null) {
                Toast.makeText(this, "Invalid datetime or seats", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.add(conducteurEmail, origin, dest, dt, seats, notes)
            Toast.makeText(this, "Traject added", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
