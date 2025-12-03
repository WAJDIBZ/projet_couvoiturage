package com.example.projet_couvoiturage.ui.traject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.projet_couvoiturage.R

class ConducteurHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conducteur_home)

        findViewById<Button>(R.id.btn_add_traject).setOnClickListener {
            startActivity(Intent(this, AddTrajectActivity::class.java))
        }

        findViewById<Button>(R.id.btn_my_trajects).setOnClickListener {
            startActivity(Intent(this, MyTrajectsActivity::class.java))
        }

        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            com.example.projet_couvoiturage.auth.SessionManager.logout()
            val intent = Intent(this, com.example.projet_couvoiturage.ui.auth.LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
