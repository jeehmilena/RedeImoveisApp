package com.jess.zapchallenge.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jess.zapchallenge.Constants.LIST_PROPERTIES
import com.jess.zapchallenge.Constants.TYPE_GROUP
import com.jess.zapchallenge.R
import com.jess.zapchallenge.home.model.PropertieResultItem
import com.jess.zapchallenge.home.view.adapter.PropertiesAdapter
import com.jess.zapchallenge.home.viewmodel.PropertieViewModel
import com.jess.zapchallenge.home.viewmodel.propertieevent.PropertieEvent
import com.jess.zapchallenge.home.viewmodel.propertieinteractor.PropertieInteractor
import com.jess.zapchallenge.home.viewmodel.propertiestate.PropertieState
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private val _viewModel: PropertieViewModel by activityViewModels()
    private var _propertieGroup: Long = 1
    private lateinit var _listProperties: ArrayList<PropertieResultItem>
    private val _adapter: PropertiesAdapter by lazy {
        PropertiesAdapter(
            ArrayList()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(TYPE_GROUP) }?.apply {
            _propertieGroup = getLong(TYPE_GROUP)
            _listProperties =
                getParcelableArrayList<PropertieResultItem>(LIST_PROPERTIES) as ArrayList<PropertieResultItem>
        }

        _listProperties = _viewModel.listFilter(_propertieGroup.toInt())
        _adapter.list = _listProperties

        recyclerViewImoveis.layoutManager = LinearLayoutManager(context)
        recyclerViewImoveis.adapter = _adapter
    }
}