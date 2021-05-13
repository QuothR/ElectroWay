package com.github.electroway.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.R

internal class PlugListAdapter(
    private var itemsList: MutableList<Int>,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<PlugListAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stationText: TextView = view.findViewById(R.id.plugText)

        fun bind(onClick: (Int) -> Unit) {
            itemView.findViewById<ImageButton>(R.id.removePlugButton).setOnClickListener {
                onClick(itemsList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.plugs_recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.stationText.text = "Plug ${itemsList[position]}"
        holder.bind(onClick)
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }

    fun add(index: Int) {
        itemsList.add(index)
    }

    fun getNextIndex(): Int {
        return itemsList.size + 1
    }
}