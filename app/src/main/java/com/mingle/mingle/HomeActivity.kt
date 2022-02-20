package com.mingle.mingle

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.mingle.mingle.adaptors.BottomNavAdapter
import com.mingle.mingle.databinding.ActivityHomeBinding
import com.mingle.mingle.fragments.FindAFriendFragment
import com.mingle.mingle.fragments.ProfileFragment

class HomeActivity : MyBaseClass() {

    lateinit var bottomNavAdapter: BottomNavAdapter
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)
        addCommonViews(view, this)
        setViewPager()

    }

    private fun setViewPager() {
        bottomNavAdapter = BottomNavAdapter(supportFragmentManager)
        bottomNavAdapter.addFragment(ProfileFragment.newInstance())
        bottomNavAdapter.addFragment(FindAFriendFragment.newInstance())
        bottomNavAdapter.addFragment(ProfileFragment.newInstance())
        bottomNavAdapter.addFragment(ProfileFragment.newInstance())
        bottomNavAdapter.addFragment(ProfileFragment.newInstance())
        bottomNavAdapter.addFragment(ProfileFragment.newInstance())

        binding.mainViewpager.adapter = bottomNavAdapter
        binding.bottomTabLayout.setupWithViewPager(binding.mainViewpager)
        binding.mainViewpager.offscreenPageLimit = bottomNavAdapter.count - 1
        for (i in 0 until bottomNavAdapter.count) {
            val customView = layoutInflater.inflate(R.layout.custom_tab_layout, null)
            val image = customView.findViewById<ImageView>(R.id.icon)
            var icon: Int? = null
            when (i) {
                0 -> icon = R.drawable.ic_user_unfilled
                1 -> icon = R.drawable.ic_friend_unfilled
                2 -> icon = R.drawable.ic_event_unfilled
                3 -> icon = R.drawable.ic_email_unfilled
                4 -> icon = R.drawable.ic_journal_unfilled
                5 -> icon = R.drawable.ic_setting_unfilled
            }

            if(i==1){
                image.setColorFilter(Color.argb(255, 255, 206, 53));
            }

            Glide.with(this@HomeActivity)
                .load(icon).into(image)
            binding.bottomTabLayout.getTabAt(i)?.customView = customView
        }
        addBottomNavListener()
    }


    private fun changeIconsAccordingly(i: Int, tab: TabLayout.Tab?, img: ImageView) {
        img.setColorFilter(Color.argb(255, 59, 85, 217));
        when (i) {
            0 -> {
                var icon = R.drawable.ic_user_unfilled
                if (i == tab?.position) {
                    icon = R.drawable.ic_user_unfilled
                    img.setColorFilter(Color.argb(255, 255, 206, 53));
                }
                Glide.with(this@HomeActivity)
                    .load(icon).into(img)
            }
            1 -> {
                var icon = R.drawable.ic_friend_unfilled
                if (i == tab?.position) {
                    icon = R.drawable.ic_friend_unfilled
                    img.setColorFilter(Color.argb(255, 255, 206, 53));
                }
                Glide.with(this@HomeActivity)
                    .load(icon).into(img)
            }
            2 -> {
                var icon = R.drawable.ic_event_unfilled
                if (i == tab?.position) {
                    icon = R.drawable.ic_event_unfilled
                    img.setColorFilter(Color.argb(255, 255, 206, 53));
                }
                Glide.with(this@HomeActivity)
                    .load(icon).into(img)
            }
            3 -> {
                var icon = R.drawable.ic_email_unfilled
                if (i == tab?.position) {
                    icon = R.drawable.ic_email_unfilled
                    img.setColorFilter(Color.argb(255, 255, 206, 53));
                }
                Glide.with(this@HomeActivity)
                    .load(icon).into(img)
            }
            4 -> {
                var icon = R.drawable.ic_journal_unfilled
                if (i == tab?.position) {
                    icon = R.drawable.ic_journal_unfilled
                    img.setColorFilter(Color.argb(255, 255, 206, 53));
                }
                Glide.with(this@HomeActivity)
                    .load(icon).into(img)
            }
            5 -> {
                var icon = R.drawable.ic_setting_unfilled
                if (i == tab?.position) {
                    icon = R.drawable.ic_setting_unfilled
                    img.setColorFilter(Color.argb(255, 255, 206, 53));
                }
                Glide.with(this@HomeActivity)
                    .load(icon).into(img)
            }
        }
    }

    private fun addBottomNavListener() {

        val bottomTabLayout = binding.bottomTabLayout

        bottomTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                for (i in 0 until bottomNavAdapter.count) {
                    val img =
                        bottomTabLayout.getTabAt(i)?.customView!!.findViewById<ImageView>(R.id.icon)
                    changeIconsAccordingly(i, tab, img)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        bottomTabLayout.getTabAt(1)?.select()
    }


}