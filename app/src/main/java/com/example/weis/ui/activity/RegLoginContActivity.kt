package com.example.weis.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.weis.R
import com.example.weis.databinding.ActivityRegLoginContBinding
import com.example.weis.ui.fragment.LoginFragment
import com.example.weis.ui.fragment.RegistrationFragment
import com.example.weis.utils.LogReg

class RegLoginContActivity : AppCompatActivity() {

var pageMode = LogReg.REGISTERATION
    private lateinit var binding : ActivityRegLoginContBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegLoginContBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)

        binding.textSwitchRegLog.text = getString(R.string.concat, getString(R.string.don_t_have_an_account), getString(R.string.sign_up))

        binding.textSwitchRegLog.setOnClickListener{
            if(pageMode == LogReg.REGISTERATION){
                binding.textSwitchRegLog.text = getString(R.string.concat, getString(R.string.already_have_an_account), getString(R.string.sign_in))
                changeFragment(LoginFragment())
                pageMode = LogReg.LOGIN
            }else{
                binding.textSwitchRegLog.text = getString(R.string.concat, getString(R.string.don_t_have_an_account), getString(R.string.sign_up))
                changeFragment(RegistrationFragment())
                pageMode = LogReg.REGISTERATION
            }
        }

    }

    private fun changeFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragContRegLog, fragment)
            .commit()
    }
}