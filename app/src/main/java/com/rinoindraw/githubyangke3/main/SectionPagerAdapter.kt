package com.rinoindraw.githubyangke3.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rinoindraw.githubyangke3.main.follow.FragmentFollowers
import com.rinoindraw.githubyangke3.main.follow.FragmentFollowing

class SectionPagerAdapter(activity: AppCompatActivity, private val login: Bundle) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentFollowers()
            1 -> fragment = FragmentFollowing()
        }
        fragment?.arguments = login
        return fragment as Fragment
    }

}