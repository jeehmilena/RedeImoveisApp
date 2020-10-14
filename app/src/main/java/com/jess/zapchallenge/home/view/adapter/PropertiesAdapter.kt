package com.jess.zapchallenge.home.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jess.zapchallenge.R
import com.jess.zapchallenge.home.model.PropertieResultItem
import com.squareup.picasso.Picasso

class PropertiesAdapter(
    var list: MutableList<PropertieResultItem>
) : RecyclerView.Adapter<PropertiesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_imovel_recyclerview, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: PropertiesAdapter.ViewHolder, position: Int) =
        holder.onBind(list[position])

    override fun getItemCount() = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var propertieImage = itemView.findViewById<ImageView>(R.id.imovel_image)
        private var propertieType = itemView.findViewById<TextView>(R.id.imovel_type)
        private var propertieNeighborhood =
            itemView.findViewById<TextView>(R.id.imovel_neighborhood)
        private var propertieCity = itemView.findViewById<TextView>(R.id.imovel_city)
        private var propertieUsableAreas = itemView.findViewById<TextView>(R.id.imovel_usableAreas)
        private var propertieBadroom = itemView.findViewById<TextView>(R.id.imovel_badroom)
        private var propertieBathroom = itemView.findViewById<TextView>(R.id.imovel_bathrooms)
        private var propertieParkingSpaces =
            itemView.findViewById<TextView>(R.id.imovel_parkingSpaces)
        private var propertiePrice =
            itemView.findViewById<TextView>(R.id.imovel_price)

        fun onBind(propertie: PropertieResultItem) {
            Picasso.get().load(propertie.images[0]).into(propertieImage)
            propertieType.text = propertie.pricingInfos.businessType
            propertieNeighborhood.text = propertie.address.neighborhood
            propertieCity.text = propertie.address.city
            propertieUsableAreas.text = propertie.usableAreas.toString()
            propertieBadroom.text = propertie.bedrooms.toString()
            propertieBathroom.text = propertie.bathrooms.toString()
            propertieParkingSpaces.text = propertie.parkingSpaces.toString()
            propertiePrice.text =
                itemView.resources.getString(
                    R.string.propertie_price, propertie.pricingInfos.price
                )
            propertiePrice.text = itemView.resources.getString(
                R.string.propertie_price,
                propertie.pricingInfos.price
            )
        }
    }

    fun update(list: MutableList<PropertieResultItem>) {

        if (this.list.isEmpty()) {
            this.list = list as ArrayList<PropertieResultItem>
        } else {
            this.list.addAll(list)
        }
        notifyDataSetChanged()
    }

}

