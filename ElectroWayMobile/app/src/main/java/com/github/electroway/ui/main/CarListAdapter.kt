package com.github.electroway.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.CarInfo
import com.github.electroway.R

internal class CarListAdapter(
    private var itemsList: MutableList<CarInfo>,
    private val onClick: (CarInfo) -> Unit
) :
    RecyclerView.Adapter<CarListAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val carText: TextView = view.findViewById(R.id.carText)

        fun bind(onClick: (CarInfo) -> Unit) {
            itemView.setOnClickListener {
                onClick(itemsList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cars_recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.carText.text = itemsList[position].model
        holder.bind(onClick)
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }

    fun add(car: CarInfo) {
        itemsList.add(car)
    }
}