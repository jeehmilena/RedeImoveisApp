package com.jess.zapchallenge.home.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jess.zapchallenge.R
import com.jess.zapchallenge.home.view.adapter.PropertiesPageAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val pageAdapter: PropertiesPageAdapter by lazy {
        PropertiesPageAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val titulos = arrayListOf<String>()

        titulos.add("Grupo ZAP")
        titulos.add("Viva Real")

        view_pager.adapter = pageAdapter

        TabLayoutMediator(tabs, view_pager,
            fun(tab: TabLayout.Tab, position: Int) {
                tab.text = titulos[position]
            }).attach()
    }
}