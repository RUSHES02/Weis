package com.example.weis.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weis.R
import com.example.weis.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}