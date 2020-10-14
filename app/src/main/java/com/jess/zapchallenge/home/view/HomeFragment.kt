package com.jess.zapchallenge.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jess.zapchallenge.Constants.LIST_PROPERTIES
import com.jess.zapchallenge.Constants.TYPE_GROUP
import com.jess.zapchallenge.R
import com.jess.zapchallenge.home.model.PropertieResultItem
import com.jess.zapchallenge.home.view.adapter.PropertiesAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private val adapter: PropertiesAdapter by lazy {
        PropertiesAdapter(
            ArrayList()
        )
    }

    private var propertieGroup: Long = 1
    private lateinit var listProperties: ArrayList<PropertieResultItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(TYPE_GROUP) }?.apply {
            propertieGroup = getLong(TYPE_GROUP)
            listProperties =
                getParcelableArrayList<PropertieResultItem>(LIST_PROPERTIES) as ArrayList<PropertieResultItem>
        }

        adapter.list = listProperties

        recyclerViewImoveis.layoutManager = LinearLayoutManager(context)
        recyclerViewImoveis.adapter = adapter
    }
}