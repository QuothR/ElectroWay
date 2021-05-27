package com.github.electroway.ui.stations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.R

internal class StationListAdapter(
    private var itemsList: MutableList<Pair<Int, String>>,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<StationListAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stationText: TextView = view.findViewById(R.id.stationText)

        fun bind(onClick: (Int) -> Unit) {
            itemView.setOnClickListener {
                onClick(itemsList[adapterPosition].first)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.stations_recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.stationText.text = itemsList[position].second
        holder.bind(onClick)
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }

    fun add(index: Int, address: String) {
        itemsList.add(Pair(index, address))
    }
}