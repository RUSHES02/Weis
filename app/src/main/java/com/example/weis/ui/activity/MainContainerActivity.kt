package com.example.weis.ui.activity

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.weis.R
import com.example.weis.databinding.ActivityMainContainerBinding
import com.example.weis.adapter.ViewPagerAdapter
import com.example.weis.modals.Goal
import com.example.weis.ui.fragment.mainFragments.HomeFragment
import com.example.weis.ui.fragment.mainFragments.focusFragments.FocusFragment
import com.example.weis.ui.fragment.mainFragments.goalFragments.GoalsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class MainContainerActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainContainerBinding
    private lateinit var viewPagerAdapter : ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setting up the binder class
        binding = ActivityMainContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setting the status bar color
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)

        //initializing and setting up hte view pager with the fragments
        viewPagerAdapter = ViewPagerAdapter(this)
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
        //setting the default fragment
        binding.viewPagerMain.setCurrentItem(1, true)

        //attaching the tab layout with the view pager
        TabLayoutMediator(binding.tabLayout, binding.viewPagerMain) { tab, position ->
            val customTabView = viewPagerAdapter.getTabView(position)
            tab.customView = customTabView
            //selecting the second tab
            if(position == 1){
                setTab(tab, R.color.primaryColor)
            }
        }.attach()
        binding.tabLayout.getTabAt(1)?.select()

        //to specify the changes while a tab is selected or unselected
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                setTab(tab, R.color.primaryColor)
                //if the first tab is selected hide the tab layout
                if(tab?.position == 0){
                    binding.cardTabLay.visibility = View.GONE
                }else{
                    binding.cardTabLay.visibility = View.VISIBLE
                }
            }

            //when a tab is unselected
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                setTab(tab, R.color.grey)
            }

            //when a tab is unselected
            override fun onTabReselected(tab: TabLayout.Tab?) {
                setTab(tab, R.color.primaryColor)
            }

        })
    }

    //function to set the tab layout and the color
    private fun setTab(tab : TabLayout.Tab?, color : Int){
        tab?.let {
            val customTabView = it.customView
            val tabIconImageView = customTabView?.findViewById<ImageView>(R.id.tabIcon)
            val tabNameTextView = customTabView?.findViewById<TextView>(R.id.tabName)

            tabIconImageView?.setColorFilter(ContextCompat.getColor(this@MainContainerActivity, color))
            tabNameTextView?.setTextColor(ContextCompat.getColor(this@MainContainerActivity, color))
        }
    }

    //function to change the color of an xml icon drawable
    private fun getColorIcon(icon: Int, color: Int): Drawable? {
        val drawable = ContextCompat.getDrawable(this, icon)?.mutate()
        drawable?.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_IN)
        return drawable
    }

    fun changeTab(position : Int, goal : Goal) {
        Log.d("Start button","main container")
        binding.viewPagerMain.setCurrentItem(position, true)
        when(val frag = getCurrentFrag()){
            is FocusFragment -> frag.receiveData(goal)
        }
    }

    private fun getCurrentFrag() = supportFragmentManager.findFragmentByTag("f${binding.viewPagerMain.currentItem}")
//    override fun sendData(data: Goal?) {
////        val tag = "android:switcher:"+R.id.viewPagerMain.toString()+":"+1
//        val fm = FocusFragment()
//        fm.receiveData(data)
//    }
}