package com.example.projet_couvoiturage.ui.employee

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.projet_couvoiturage.R
import com.example.projet_couvoiturage.databinding.ActivityEmployeeHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_employee) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavEmployee.setupWithNavController(navController)
    }
}
