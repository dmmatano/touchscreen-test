package com.example.displaytestgertec.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.displaytestgertec.databinding.ActivityMainBinding
import com.example.displaytestgertec.ui.touchtest.TouchTestActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtons()
    }

    private fun setupButtons() {
        binding.btnInitTest.setOnClickListener {
            startActivity(Intent(this, TouchTestActivity::class.java))
        }
    }

}