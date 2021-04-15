package com.github.electroway.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.R

internal class HistoryAdapter(private var itemsList: List<Pair<String, Int>>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemTextView: TextView = view.findViewById(R.id.itemTextView)
        val itemImageView: ImageView = view.findViewById(R.id.itemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = itemsList[position].first
        val resource = itemsList[position].second
        holder.itemTextView.text = name
        holder.itemImageView.tag = resource
        holder.itemImageView.setBackgroundResource(resource)
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }
}