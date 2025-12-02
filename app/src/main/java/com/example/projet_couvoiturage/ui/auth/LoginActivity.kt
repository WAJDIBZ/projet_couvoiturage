package com.example.projet_couvoiturage.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projet_couvoiturage.R
import com.example.projet_couvoiturage.auth.AuthRepository
import com.example.projet_couvoiturage.auth.SessionManager
import com.example.projet_couvoiturage.ui.admin.AdminDashboardActivity
import com.example.projet_couvoiturage.ui.employee.EmployeeHomeActivity
import com.example.projet_couvoiturage.ui.traject.ConducteurHomeActivity
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

        btn.setOnClickListener {
            val e = email.text.toString().trim()
            val p = pass.text.toString()
            if (e.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Enter email & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val repo = AuthRepository(this)
            CoroutineScope(Dispatchers.IO).launch {
                val role = repo.login(e, p)
                runOnUiThread {
                    if (role == null) {
                        Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    } else {
                        when (role) {
                            SessionManager.Role.ADMIN -> startActivity(Intent(this@LoginActivity, AdminDashboardActivity::class.java))
                            SessionManager.Role.USER -> startActivity(Intent(this@LoginActivity, EmployeeHomeActivity::class.java))
                            SessionManager.Role.CONDUCTEUR -> startActivity(Intent(this@LoginActivity, ConducteurHomeActivity::class.java))
                        }
                        finish()
                    }
                }
            }
        }

        btnReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
