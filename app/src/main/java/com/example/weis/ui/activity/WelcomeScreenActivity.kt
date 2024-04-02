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
        super.onCreate(savedInstanceState)

        //setting the binder class
        binder = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binder.root)

        //setting the color of the status bar
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)

        //initializing the firebase auth
        val auth = FirebaseAuth.getInstance().currentUser

        //setting the welcome page according to hte new or existing user
        val intent = if(auth != null){
            //if the user exists
            binder.btnNext.visibility = View.GONE
            Intent(this, MainContainerActivity::class.java)
        }else{
            //if the user is a new user
            binder.btnNext.visibility = View.VISIBLE
            Intent(this, RegLoginContActivity::class.java)
        }

        //navigating to the page accordingly with i sec delay
        Handler().postDelayed({
            if(auth != null){
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