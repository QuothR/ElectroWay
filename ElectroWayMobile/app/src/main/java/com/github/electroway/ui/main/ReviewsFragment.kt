package com.github.electroway.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.Application
import com.github.electroway.R

class ReviewsFragment : Fragment() {
    private val args: ReviewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.addReviewButton).setOnClickListener {
            val action = ReviewsFragmentDirections.actionReviewsFragmentToAddReviewFragment(args.station)
            findNavController().navigate(action)
        }

        view.findViewById<ImageButton>(R.id.reviewsBackButton).setOnClickListener {
            findNavController().navigate(R.id.action_reviewsFragment_to_mapFragment)
        }

        val session = (requireActivity().application as Application).session
        val recyclerView = view.findViewById<RecyclerView>(R.id.reviewsRecyclerView)
        val adapter = ReviewsAdapter()
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        session.getReviews(args.station) {
            if (it != null) {
                for (i in 0 until it.length()) {
                    val obj = it.getJSONObject(i)
                    val text = obj.getString("textReview")
                    val rating = obj.getInt("rating")
                    adapter.add(rating, text)
                }
                adapter.notifyDataSetChanged()
            }
        }
    }
}