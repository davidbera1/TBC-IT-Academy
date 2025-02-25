package com.example.learnandroid.presentation.bottom_nav_bar_container

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.learnandroid.presentation.favorites.FavoritesFragment
import com.example.learnandroid.presentation.home.HomeFragment
import com.example.learnandroid.presentation.profile.ProfileFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> FavoritesFragment()
            2 -> ProfileFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}