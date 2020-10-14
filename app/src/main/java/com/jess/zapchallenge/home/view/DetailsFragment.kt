package com.jess.zapchallenge.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.jess.zapchallenge.Constants.PROPERTIE_DETAIL_KEY
import com.jess.zapchallenge.R
import com.jess.zapchallenge.home.model.PropertieResultItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showInfoDetailsPropertie()
    }

    private fun showInfoDetailsPropertie() {
        val propertie = arguments?.getParcelable<PropertieResultItem>(PROPERTIE_DETAIL_KEY)

        Picasso.get().load(propertie?.images?.get(0)).into(imovel_image_detail)
        imovel_badroom_detail.text = propertie?.bedrooms.toString()
        imovel_bathrooms_detail.text = propertie?.bathrooms.toString()
        imovel_city_detail.text = propertie?.address?.city
        imovel_neighborhood_detail.text = propertie?.address?.neighborhood
        imovel_parkingSpaces_detail.text = propertie?.parkingSpaces.toString()
        imovel_price_detail.text = propertie?.pricingInfos?.price
        imovel_usableAreas_detail.text = propertie?.usableAreas.toString()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavHostFragment.findNavController(this).popBackStack()
            }
        }
        return true
    }

}