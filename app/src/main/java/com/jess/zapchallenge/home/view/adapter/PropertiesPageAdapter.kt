package com.jess.zapchallenge.home.view.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jess.zapchallenge.Constants.LIST_PROPERTIES
import com.jess.zapchallenge.Constants.TYPE_GROUP
import com.jess.zapchallenge.home.model.PropertieResultItem
import com.jess.zapchallenge.home.view.PropertiesFragment

class PropertiesPageAdapter(fragment: Fragment, var list: ArrayList<PropertieResultItem>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val homeFragment = PropertiesFragment()
        homeFragment.arguments = Bundle().apply {
            putLong(TYPE_GROUP, position.toLong())
            putParcelableArrayList(LIST_PROPERTIES, list)
        }
        return homeFragment
    }

    fun update(list: MutableList<PropertieResultItem>) {

        if (this.list.isEmpty()) {
            this.list = list as ArrayList<PropertieResultItem>
        } else {
            this.list.addAll(list)
        }
        notifyDataSetChanged()
    }
}