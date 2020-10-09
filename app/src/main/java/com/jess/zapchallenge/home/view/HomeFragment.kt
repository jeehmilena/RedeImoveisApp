package com.jess.zapchallenge.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jess.zapchallenge.R
import com.jess.zapchallenge.home.model.PropertieResultItem
import com.jess.zapchallenge.home.view.adapter.PropertiesAdapter
import com.jess.zapchallenge.home.view.adapter.TYPE_GROUP
import com.jess.zapchallenge.home.viewmodel.PropertieViewModel
import com.jess.zapchallenge.home.viewmodel.propertieevent.PropertieEvent
import com.jess.zapchallenge.home.viewmodel.propertieinteractor.PropertieInteractor
import com.jess.zapchallenge.home.viewmodel.propertiestate.PropertieState
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private val adapter: PropertiesAdapter by lazy {
        PropertiesAdapter(
            ArrayList()
        )
    }

    private val viewModel: PropertieViewModel by lazy {
        ViewModelProvider(this).get(
            PropertieViewModel::class.java
        )
    }

    private var seasonNumber: Long = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewImoveis.layoutManager = LinearLayoutManager(context)
        recyclerViewImoveis.adapter = adapter

        arguments?.takeIf { it.containsKey(TYPE_GROUP) }?.apply {
            seasonNumber = getLong(TYPE_GROUP)
        }

        initViewModel()
    }

    private fun initViewModel() {

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            state?.let {
                when (it) {
                    is PropertieState.PropertiesListSuccess -> showListProperties(it.properties)
                    is PropertieState.PropertiesListError -> showErrorMessage(it.messageError)
                }
            }
        })

        viewModel.event.observe(viewLifecycleOwner, Observer { event ->
            event?.let {
                when (it) {
                    is PropertieEvent.Loading -> showLoading(it.status)
                }
            }
        })

        viewModel.interpret(PropertieInteractor.ShowList)
    }

    private fun showListProperties(properties: List<PropertieResultItem>) {
        adapter.update(properties.toMutableList())
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(recyclerViewImoveis, message, Snackbar.LENGTH_LONG).show()
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