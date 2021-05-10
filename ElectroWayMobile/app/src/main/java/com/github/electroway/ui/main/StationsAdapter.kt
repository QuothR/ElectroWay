package com.github.electroway.ui.main

import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.R
import com.google.android.gms.maps.model.LatLng

internal class StationsAdapter(private var itemsList: MutableList<String>) :
    RecyclerView.Adapter<StationsAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stationText: TextView = view.findViewById(R.id.stationText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.stations_recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.stationText.text = itemsList[position]
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }

    fun add(address: String) {
        itemsList.add(address)
    }
}