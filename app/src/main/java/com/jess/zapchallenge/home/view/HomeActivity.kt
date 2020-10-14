package com.jess.zapchallenge.home.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jess.zapchallenge.Constants.GRUPO_ZAP
import com.jess.zapchallenge.Constants.VIVA_REAL
import com.jess.zapchallenge.R
import com.jess.zapchallenge.home.model.PropertieResultItem
import com.jess.zapchallenge.home.view.adapter.PropertiesPageAdapter
import com.jess.zapchallenge.home.viewmodel.PropertieViewModel
import com.jess.zapchallenge.home.viewmodel.propertieevent.PropertieEvent
import com.jess.zapchallenge.home.viewmodel.propertieinteractor.PropertieInteractor
import com.jess.zapchallenge.home.viewmodel.propertiestate.PropertieState
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val titles = arrayListOf(GRUPO_ZAP, VIVA_REAL)
    private val pageAdapter: PropertiesPageAdapter by lazy {
        PropertiesPageAdapter(this, ArrayList())
    }

    private val viewModel: PropertieViewModel by lazy {
        ViewModelProvider(this).get(
            PropertieViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViewModel()

        view_pager.adapter = pageAdapter

        TabLayoutMediator(tabs, view_pager,
            fun(tab: TabLayout.Tab, position: Int) {
                tab.text = titles[position]
            }).attach()
    }

    private fun initViewModel() {

        viewModel.state.observe(this, { state ->
            state?.let {
                when (it) {
                    is PropertieState.PropertiesListSuccess -> sendList(it.properties)
                }
            }
        })

        viewModel.event.observe(this, { event ->
            event?.let {
                when (it) {
                    is PropertieEvent.Loading -> showLoading(it.status)
                }
            }
        })

        viewModel.interpret(PropertieInteractor.ShowList)
    }

    private fun sendList(list: ArrayList<PropertieResultItem>) {
        pageAdapter.update(list)
        view_pager.adapter = pageAdapter
    }

    private fun showLoading(status: Boolean) {
        when {
            status -> {
                loading.visibility = View.VISIBLE
            }
            else -> {
                loading.visibility = View.GONE
            }
        }
    }
}