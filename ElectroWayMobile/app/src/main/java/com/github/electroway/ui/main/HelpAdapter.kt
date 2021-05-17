package com.github.electroway.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.R
import org.w3c.dom.Text

internal class HelpAdapter(private var itemsList: List<Pair<String, String>>) :
    RecyclerView.Adapter<HelpAdapter.ViewHolder>() {
    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questionTextView = view.findViewById<TextView>(R.id.questionTextView)
        val answerTextView = view.findViewById<TextView>(R.id.answerTextView)
        val toggleButton = view.findViewById<ToggleButton>(R.id.toggleButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.help_recycler_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.questionTextView.text = itemsList[position].first
        holder.answerTextView.text = itemsList[position].second
        holder.toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.answerTextView.visibility = View.VISIBLE
            } else {
                holder.answerTextView.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsList.count()
    }
}