package com.jess.zapchallenge.home.view.adapter

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jess.zapchallenge.home.model.PageAdapterItem

class PropertiesPageAdapter(
    var fragments: MutableList<PageAdapterItem>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position].fragment
}