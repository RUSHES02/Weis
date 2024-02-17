package com.example.weis.ui.activity.activity

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.weis.R
import com.example.weis.databinding.ActivityHomeBinding

class MainContainerActivity : AppCompatActivity() {

    private lateinit var binder : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binder.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)

        binder.cardCenterIC.drawable.colorFilter = PorterDuffColorFilter(getColor(R.color.primaryColor), PorterDuff.Mode.SRC_IN)
    }
}