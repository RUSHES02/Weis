package com.example.weis.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.weis.R
import com.example.weis.databinding.ActivityRegLoginContBinding

class RegLoginContActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegLoginContBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegLoginContBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)

    }
}