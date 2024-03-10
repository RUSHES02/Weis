package com.example.weis.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import com.example.weis.R
import com.example.weis.databinding.ActivityWelcomeScreenBinding
import com.google.firebase.auth.FirebaseAuth

class WelcomeScreenActivity : AppCompatActivity() {

    private lateinit var binder: ActivityWelcomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.Theme_AnimatedSplash_MySplash)

        super.onCreate(savedInstanceState)

        window.setBackgroundDrawableResource(R.drawable.splash_weis)

        binder = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binder.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)

//        installSplashScreen().setKeepOnScreenCondition{
//            true
//        }

        val auth = FirebaseAuth.getInstance().currentUser
        val intent = if(auth != null){
            binder.btnNext.visibility = View.GONE
            Intent(this, MainContainerActivity::class.java)
        }else{
            Intent(this, RegLoginContActivity::class.java)
        }

        Handler().postDelayed({
            if(binder.btnNext.visibility == View.GONE){
                startActivity(intent)
                finish()
            }else{
                binder.btnNext.setOnClickListener{
                    startActivity(intent)
                    finish()
                }
            }

        }, 1000)
    }
}