package com.vichita.flightsearch.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.vichita.flightsearch.R
import com.vichita.flightsearch.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_navigation_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

    }
}