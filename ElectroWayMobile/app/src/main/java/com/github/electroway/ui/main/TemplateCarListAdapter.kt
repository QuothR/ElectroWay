package com.github.electroway.ui.main

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.CarInfo
import com.github.electroway.R
import java.lang.StringBuilder

internal class TemplateCarListAdapter(
    private var itemsList: MutableList<CarInfo>,
    private val onClick: (CarInfo) -> Unit,
) :
    RecyclerView.Adapter<TemplateCarListAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val templateCarText: TextView = view.findViewById(R.id.templateCarText)

        fun bind(onClick: (CarInfo) -> Unit) {
            itemView.setOnClickListener {
                onClick(itemsList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_car_recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(onClick)
        holder.templateCarText.text =
            Html.fromHtml(
                StringBuilder().append("<b>${itemsList[position].model}, ${itemsList[position].year}</b>")
                    .append(", Battery capacity: ${itemsList[position].batteryCapacity}")
                    .append(", Charging capacity: ${itemsList[position].chargingCapacity}")
                    .append(", Max speed: ${itemsList[position].vehicleMaxSpeed}")
                    .toString(), 0
            )
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }

    fun add(car: CarInfo) {
        itemsList.add(car)
    }
}