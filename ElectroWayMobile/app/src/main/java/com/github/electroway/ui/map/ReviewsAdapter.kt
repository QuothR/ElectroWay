package com.github.electroway.ui.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.R

internal class ReviewsAdapter() :
    RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reviewRating: RatingBar = view.findViewById(R.id.reviewRating)
        val reviewText: TextView = view.findViewById(R.id.reviewText)
    }

    private val itemsList: MutableList<Pair<Int, String>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.reviews_recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.reviewRating.rating = itemsList[position].first.toFloat() / 2.0f
        holder.reviewText.text = itemsList[position].second
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }

    fun add(rating: Int, text: String) {
        itemsList.add(Pair(rating, text))
    }
}