package com.jess.zapchallenge.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jess.zapchallenge.Constants.LIST_PROPERTIES
import com.jess.zapchallenge.Constants.PROPERTIE_DETAIL_KEY
import com.jess.zapchallenge.R
import com.jess.zapchallenge.home.model.PropertieResultItem
import com.jess.zapchallenge.home.view.adapter.PropertiesAdapter
import kotlinx.android.synthetic.main.fragment_properties.*

class PropertiesFragment : Fragment() {
    private var listProperties = ArrayList<PropertieResultItem>()

    private val adapter: PropertiesAdapter by lazy {
        PropertiesAdapter(
            ArrayList(), this::propertieDetail
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_properties, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showList()
    }

    private fun showList() {
        arguments?.apply {
            listProperties =
                getParcelableArrayList<PropertieResultItem>(LIST_PROPERTIES) as ArrayList<PropertieResultItem>
        }

        adapter.update(listProperties)
        recyclerViewImoveis.layoutManager = LinearLayoutManager(context)
        recyclerViewImoveis.adapter = adapter
    }

    private fun propertieDetail(propertie: PropertieResultItem) {
        val bundle = bundleOf(PROPERTIE_DETAIL_KEY to propertie)
        NavHostFragment.findNavController(this).navigate(
            R.id.action_homeFragment_to_detailsFragment, bundle
        )
    }
}