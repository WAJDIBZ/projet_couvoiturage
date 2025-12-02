package com.example.projet_couvoiturage.ui.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projet_couvoiturage.R
import com.example.projet_couvoiturage.data.local.AppDatabase
import com.example.projet_couvoiturage.data.entity.Conducteur
import com.example.projet_couvoiturage.util.HashUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val name = findViewById<EditText>(R.id.input_name)
        val email = findViewById<EditText>(R.id.input_email)
        val addr = findViewById<EditText>(R.id.input_address)
        val pass = findViewById<EditText>(R.id.input_password)
        val btn = findViewById<Button>(R.id.btn_register)

        val db = AppDatabase.get(this)
        btn.setOnClickListener {
            val e = email.text.toString().trim()
            val a = addr.text.toString().trim()
            val n = name.text.toString().trim().ifEmpty { null }
            val p = pass.text.toString()

            if (e.isEmpty() || a.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Fill required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val existing = db.conducteurDao().getByEmail(e)
                if (existing != null) {
                    runOnUiThread {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Email already used",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }
                val ok = db.conducteurDao().insert(
                    Conducteur(email = e, passwordHash = HashUtil.sha256(p), address = a, name = n)
                )
                runOnUiThread {
                    if (ok > 0) {
                        Toast.makeText(this@RegisterActivity, "Registered", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
