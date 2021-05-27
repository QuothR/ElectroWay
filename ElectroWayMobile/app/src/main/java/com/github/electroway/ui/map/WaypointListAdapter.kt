package com.github.electroway.ui.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.R

internal class WaypointListAdapter(
    private var itemsList: MutableList<String>,
) :
    RecyclerView.Adapter<WaypointListAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val waypointText: TextView = view.findViewById(R.id.waypointText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.waypoints_recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.waypointText.text = itemsList[position]
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }

    fun add(title: String) {
        itemsList.add(title)
    }
}