package com.github.electroway.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.ChargingPlugInfo
import com.github.electroway.R
import com.google.android.gms.maps.model.LatLng

internal class FavouritesAdapter(private val onClick: (LatLng) -> Unit) :
    RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val favouriteText: TextView = view.findViewById(R.id.favouriteAddressText)

        fun bind(onClick: (LatLng) -> Unit) {
            itemView.setOnClickListener {
                onClick(itemsList[adapterPosition].second)
            }
        }
    }

    private val itemsList = mutableListOf<Pair<String, LatLng>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourites_recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.favouriteText.text = itemsList[position].first
        holder.bind(onClick)
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }

    fun add(address: String, latLng: LatLng) {
        itemsList.add(Pair(address, latLng))
    }
}