package com.example.projet_couvoiturage.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projet_couvoiturage.R
import com.example.projet_couvoiturage.auth.SessionManager
import com.example.projet_couvoiturage.data.local.AppDatabase
import com.example.projet_couvoiturage.ui.traject.AddTrajectActivity
import com.example.projet_couvoiturage.util.HashUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.login_email)
        val pass = findViewById<EditText>(R.id.login_password)
        val btn = findViewById<Button>(R.id.btn_login)
        val btnReg = findViewById<Button>(R.id.btn_go_register)

        val db = AppDatabase.get(this)

        btn.setOnClickListener {
            val e = email.text.toString().trim()
            val p = pass.text.toString()
            if (e.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Enter email & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.IO).launch {
                val user = db.conducteurDao().authenticate(e, HashUtil.sha256(p))
                runOnUiThread {
                    if (user != null) {
                        SessionManager.currentEmail = user.email
                        Toast.makeText(this@LoginActivity, "Welcome ${'$'}{user.email}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, AddTrajectActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btnReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
