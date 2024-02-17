package com.example.weis

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.weis.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binder: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binder.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)
    }
}