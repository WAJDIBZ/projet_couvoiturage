package com.example.projet_couvoiturage.ui.traject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_couvoiturage.R
import com.example.projet_couvoiturage.auth.SessionManager
import com.example.projet_couvoiturage.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyTrajectsActivity : AppCompatActivity() {

    private lateinit var adapter: TripAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_trajects)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_trips)
        val emptyView = findViewById<TextView>(R.id.tv_empty)

        adapter = TripAdapter { trip ->
            val intent = Intent(this, TrajectDetailsActivity::class.java)
            intent.putExtra("tripId", trip.id)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadTrips(emptyView)
    }

    private fun loadTrips(emptyView: View) {
        val email = SessionManager.currentEmail ?: return
        val db = AppDatabase.get(this)
        lifecycleScope.launch(Dispatchers.IO) {
            val user = db.userDao().getUserByEmail(email)
            val trips = if (user != null) db.tripDao().listByDriverId(user.id) else emptyList()
            withContext(Dispatchers.Main) {
                adapter.submitList(trips)
                emptyView.visibility = if (trips.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }
}
