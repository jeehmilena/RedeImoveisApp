package com.jess.zapchallenge.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.jess.zapchallenge.Constants
import com.jess.zapchallenge.Constants.PROPERTIE_DETAIL_KEY
import com.jess.zapchallenge.R
import com.jess.zapchallenge.formatValue
import com.jess.zapchallenge.home.model.PropertieResultItem
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.*
import kotlin.collections.ArrayList

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

        val imageList = ArrayList<SlideModel>()

        propertie?.images?.forEach {
            imageList.add(SlideModel(it))
        }

        image_slider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        imovel_badroom_detail.text = propertie?.bedrooms.toString()
        imovel_bathrooms_detail.text = propertie?.bathrooms.toString()
        imovel_city_detail.text = propertie?.address?.city
        imovel_neighborhood_detail.text = propertie?.address?.neighborhood
        imovel_parkingSpaces_detail.text = propertie?.parkingSpaces.toString()
        imovel_usableAreas_detail.text =
            resources.getString(R.string.propertie_size, propertie?.usableAreas.toString())

        when (propertie?.pricingInfos?.businessType) {
            Constants.RENTAL -> {
                imovel_price_detail.formatValue(propertie.pricingInfos.rentalTotalPrice.toDouble())
            }
            Constants.SALE -> {
                imovel_price_detail.formatValue(propertie.pricingInfos.price.toDouble())
            }
        }
    }
}