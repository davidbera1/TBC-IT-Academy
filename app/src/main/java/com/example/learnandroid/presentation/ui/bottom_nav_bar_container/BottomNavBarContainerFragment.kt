package com.example.learnandroid.presentation.ui.bottom_nav_bar_container

import androidx.viewpager2.widget.ViewPager2
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentBottomNavBarContainerBinding
import com.example.learnandroid.presentation.base.BaseFragment

class BottomNavBarContainerFragment :
    BaseFragment<FragmentBottomNavBarContainerBinding>(FragmentBottomNavBarContainerBinding::inflate) {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun start() {
        setUpViewPager()
    }

    override fun setUpListeners() {
        setUpNavBarListener()
    }

    private fun setUpViewPager() {
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager2.adapter = viewPagerAdapter

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavBar.selectedItemId = when (position) {
                    0 -> R.id.nav_bar_first
                    1 -> R.id.nav_bar_second
                    2 -> R.id.nav_bar_third
                    else -> R.id.nav_bar_first
                }
            }
        })
    }

    private fun setUpNavBarListener() {
        binding.bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_bar_first -> binding.viewPager2.setCurrentItem(0, true)
                R.id.nav_bar_second -> binding.viewPager2.setCurrentItem(1, true)
                R.id.nav_bar_third -> binding.viewPager2.setCurrentItem(2, true)
            }
            true
        }
    }
}