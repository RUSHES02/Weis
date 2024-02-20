package com.example.weis.ui.activity

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.weis.R
import com.example.weis.databinding.ActivityMainContainerBinding
import com.example.weis.adapter.ViewPagerAdapter
import com.example.weis.ui.fragment.HomeFragment
import com.example.weis.ui.fragment.FocusFragment
import com.example.weis.ui.fragment.GoalsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class MainContainerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)

        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.addFragment(
            FocusFragment(),
            getString(R.string.focus),
            getColorIcon(R.drawable.ic_weis, R.color.grey),
            getColorIcon(R.drawable.ic_weis, R.color.primaryColor)
        )
        viewPagerAdapter.addFragment(
            HomeFragment(),
            getString(R.string.home),
            getColorIcon(R.drawable.ic_home, R.color.grey),
            getColorIcon(R.drawable.ic_home, R.color.primaryColor)
        )
        viewPagerAdapter.addFragment(
            GoalsFragment(),
            getString(R.string.goals),
            getColorIcon(R.drawable.ic_notes, R.color.grey),
            getColorIcon(R.drawable.ic_notes, R.color.primaryColor)
        )

        binding.viewPagerMain.adapter = viewPagerAdapter
        binding.viewPagerMain.setCurrentItem(1, true)

        TabLayoutMediator(binding.tabLayout, binding.viewPagerMain) { tab, position ->
            val customTabView = viewPagerAdapter.getTabView(position)
            tab.customView = customTabView
            if(position == 1){
                tab.let {
                    val customTabView = it.customView
                    val tabIconImageView = customTabView?.findViewById<ImageView>(R.id.tabIcon)
                    val tabNameTextView = customTabView?.findViewById<TextView>(R.id.tabName)

                    tabIconImageView?.setColorFilter(ContextCompat.getColor(this@MainContainerActivity, R.color.primaryColor))
                    tabNameTextView?.setTextColor(ContextCompat.getColor(this@MainContainerActivity, R.color.primaryColor))
                }
            }
        }.attach()
        binding.tabLayout.getTabAt(1)?.select()

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val customTabView = it.customView
                    val tabIconImageView = customTabView?.findViewById<ImageView>(R.id.tabIcon)
                    val tabNameTextView = customTabView?.findViewById<TextView>(R.id.tabName)

                    tabIconImageView?.setColorFilter(ContextCompat.getColor(this@MainContainerActivity, R.color.primaryColor))
                    tabNameTextView?.setTextColor(ContextCompat.getColor(this@MainContainerActivity, R.color.primaryColor))
                }
                if(tab?.position == 0){
                    binding.cardTabLay.visibility = View.GONE
                }else{
                    binding.cardTabLay.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.let {
                    val customTabView = it.customView
                    val tabIconImageView = customTabView?.findViewById<ImageView>(R.id.tabIcon)
                    val tabNameTextView = customTabView?.findViewById<TextView>(R.id.tabName)

                    tabIconImageView?.setColorFilter(ContextCompat.getColor(this@MainContainerActivity, R.color.grey))
                    tabNameTextView?.setTextColor(ContextCompat.getColor(this@MainContainerActivity, R.color.grey))
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getColorIcon(icon: Int, color: Int): Drawable? {
        val drawable = ContextCompat.getDrawable(this, icon)?.mutate()
        drawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_IN)
        return drawable
    }
}



