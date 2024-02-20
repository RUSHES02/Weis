package com.example.weis.adapter

import android.graphics.drawable.Drawable
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weis.R

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){


    private val fragmentArrayList = ArrayList<Fragment>()
    private val fragmentIconResIds = ArrayList<Drawable?>()
    private val fragmentTittle = ArrayList<String>()
    private val fragmentSelectedIconResIds = ArrayList<Drawable?>()

    override fun createFragment(position: Int): Fragment {
        return fragmentArrayList[position]
    }

    override fun getItemCount(): Int {
        return fragmentArrayList.size
    }

    fun addFragment(fragment: Fragment, title: String, iconResId: Drawable?, selectedIconResId: Drawable?) {
        fragmentArrayList.add(fragment)
        fragmentTittle.add(title)
        fragmentIconResIds.add(iconResId)
        fragmentSelectedIconResIds.add(selectedIconResId)
    }

    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)


    fun getPageTitle(position: Int): String {
        return fragmentTittle[position]
    }

    fun getTabView(position: Int): View {
        val customTabView: View = layoutInflater.inflate(R.layout.tab_lay, null)

        val iconImageView: ImageView = customTabView.findViewById(R.id.tabIcon)
        val tabNameTextView: TextView = customTabView.findViewById(R.id.tabName)

        if (fragmentIconResIds[position] != null) {
            iconImageView.setImageDrawable(fragmentIconResIds[position])
        }
        tabNameTextView.text = fragmentTittle[position]
        return customTabView
    }


    fun getIconResId(position: Int): Drawable? {
        return fragmentIconResIds[position]
    }

    fun getSelectedIconResId(position: Int): Drawable? {
        return fragmentSelectedIconResIds[position]
    }
}