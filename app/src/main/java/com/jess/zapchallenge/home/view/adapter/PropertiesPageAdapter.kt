package com.jess.zapchallenge.home.view.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jess.zapchallenge.home.view.HomeFragment

const val TYPE_GROUP = "tipo de grupo"

class PropertiesPageAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val homeFragment = HomeFragment()
        homeFragment.arguments = Bundle().apply {
            putLong(TYPE_GROUP, position.toLong())
        }
        return homeFragment
    }
}