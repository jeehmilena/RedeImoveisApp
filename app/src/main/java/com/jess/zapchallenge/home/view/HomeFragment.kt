package com.jess.zapchallenge.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jess.zapchallenge.Constants.GRUPO_ZAP
import com.jess.zapchallenge.Constants.LIST_PROPERTIES
import com.jess.zapchallenge.Constants.VIVA_REAL
import com.jess.zapchallenge.R
import com.jess.zapchallenge.home.model.PageAdapterItem
import com.jess.zapchallenge.home.model.PropertieResultItem
import com.jess.zapchallenge.home.repository.PropertiesRepository
import com.jess.zapchallenge.home.view.adapter.PropertiesPageAdapter
import com.jess.zapchallenge.home.viewmodel.PropertieViewModel
import com.jess.zapchallenge.home.viewmodel.PropertieViewModelFactory
import com.jess.zapchallenge.home.viewmodel.propertieevent.PropertieEvent
import com.jess.zapchallenge.home.viewmodel.propertieinteractor.PropertieInteractor
import com.jess.zapchallenge.home.viewmodel.propertiestate.PropertieState
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment() {
    private val titles = arrayListOf(GRUPO_ZAP, VIVA_REAL)

    private val _pageAdapter: PropertiesPageAdapter by lazy {
        PropertiesPageAdapter(ArrayList(), childFragmentManager, lifecycle)
    }

    private val _viewModel: PropertieViewModel by lazy {
        val factory = PropertieViewModelFactory(Dispatchers.IO, PropertiesRepository())
        ViewModelProvider(this, factory).get(PropertieViewModel::class.java)
    }

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
    }

    private fun initViewModel() {
        _viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                when (it) {
                    is PropertieState.PropertiesListSuccess -> sendList(it.properties)
                }
            }
        })

        _viewModel.event.observe(viewLifecycleOwner, Observer { event ->
            event?.let {
                when (it) {
                    is PropertieEvent.Loading -> showLoading(it.status)
                }
            }
        })

        _viewModel.interpret(PropertieInteractor.GetList)
    }

    private fun sendList(list: ArrayList<PropertieResultItem>) {
        val itens = mutableListOf<PageAdapterItem>()

        itens.add(
            PageAdapterItem(GRUPO_ZAP, PropertiesFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(LIST_PROPERTIES, _viewModel.listFilter(0, list))
                }
            })
        )

        itens.add(
            PageAdapterItem(VIVA_REAL, PropertiesFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(LIST_PROPERTIES, _viewModel.listFilter(1, list))
                }
            })
        )

        _pageAdapter.update(itens)

        view_pager.adapter = _pageAdapter

        TabLayoutMediator(tabs, view_pager,
            fun(tab: TabLayout.Tab, position: Int) {
                tab.text = itens[position].title
            }).attach()
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