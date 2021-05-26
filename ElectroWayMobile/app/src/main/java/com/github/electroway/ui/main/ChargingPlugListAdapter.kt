package com.github.electroway.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.ChargingPlugInfo
import com.github.electroway.R

internal class ChargingPlugListAdapter(
    private var itemsList: MutableList<ChargingPlugInfo>,
    private val onClick: (ChargingPlugInfo) -> Unit
) :
    RecyclerView.Adapter<ChargingPlugListAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plugText: TextView = view.findViewById(R.id.chargingPlugText)

        fun bind(onClick: (ChargingPlugInfo) -> Unit) {
            itemView.setOnClickListener {
                onClick(itemsList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.charging_plugs_recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsList[position]
        holder.plugText.text = "${item.connectorType}, ${item.priceKw}, ${item.chargingSpeedKw}"
        holder.bind(onClick)
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }

    fun add(info: ChargingPlugInfo) {
        itemsList.add(info)
    }
}