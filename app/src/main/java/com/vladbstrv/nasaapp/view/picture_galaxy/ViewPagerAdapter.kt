package com.vladbstrv.nasaapp.view.picture_galaxy

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vladbstrv.nasaapp.view.picture_galaxy.picture_earth.EarthPictureFragment
import com.vladbstrv.nasaapp.view.picture_galaxy.picture_mars.MarsPictureFragment
import com.vladbstrv.nasaapp.view.picture_galaxy.picture_moon.MoonPictureFragment

class ViewPagerAdapter(private val fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments =
        arrayOf(EarthPictureFragment(), MarsPictureFragment(), MoonPictureFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}