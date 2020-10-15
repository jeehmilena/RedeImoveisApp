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
    var list: MutableList<PropertieResultItem>,
    val onClick: (item: PropertieResultItem) -> Unit
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

    override fun onBindViewHolder(holder: PropertiesAdapter.ViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.itemView.setOnClickListener {
            onClick(list[position])
        }
    }

    override fun getItemCount() = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var propertieImage = itemView.findViewById<ImageView>(R.id.imovel_image)
        private var propertieType = itemView.findViewById<TextView>(R.id.imovel_type)
        private var propertieNeighborhood =
            itemView.findViewById<TextView>(R.id.imovel_neighborhood)
        private var propertieCity = itemView.findViewById<TextView>(R.id.imovel_city)

        fun onBind(propertie: PropertieResultItem) {
            Picasso.get().load(propertie.images[0]).into(propertieImage)
            propertieType.text = propertie.pricingInfos.businessType
            propertieNeighborhood.text = propertie.address.neighborhood
            propertieCity.text = propertie.address.city
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

