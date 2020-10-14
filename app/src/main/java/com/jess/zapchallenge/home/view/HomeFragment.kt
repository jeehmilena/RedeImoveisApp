package com.jess.zapchallenge.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
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
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private val _titles = arrayListOf(GRUPO_ZAP, VIVA_REAL)
    private val _pageAdapter: PropertiesPageAdapter by lazy {
        PropertiesPageAdapter(this, ArrayList())
    }
    private val _viewModel: PropertieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()

        view_pager.adapter = _pageAdapter

        TabLayoutMediator(tabs, view_pager,
            fun(tab: TabLayout.Tab, position: Int) {
                tab.text = _titles[position]
            }).attach()
    }

    private fun initViewModel() {

        _viewModel.state.observe(this, { state ->
            state?.let {
                when (it) {
                    is PropertieState.PropertiesListSuccess -> sendList(it.properties)
                    is PropertieState.PropertiesListError -> errorMessage(it.messageError)
                }
            }
        })

        _viewModel.event.observe(this, { event ->
            event?.let {
                when (it) {
                    is PropertieEvent.Loading -> showLoading(it.status)
                }
            }
        })

        _viewModel.interpret(PropertieInteractor.GetList)
    }

    private fun sendList(list: ArrayList<PropertieResultItem>) {
        _pageAdapter.update(list)
        view_pager.adapter = _pageAdapter
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

    private fun errorMessage(message: String) {
        Snackbar.make(view_pager, message, Snackbar.LENGTH_LONG).show()
    }
}