package com.example.kotlin.project.mine

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.project.mine.databinding.ActivityMainBinding
import com.example.kotlin.project.mine.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}