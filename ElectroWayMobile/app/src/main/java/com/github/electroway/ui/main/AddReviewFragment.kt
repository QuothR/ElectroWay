package com.github.electroway.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.github.electroway.AddReviewInfo
import com.github.electroway.Application
import com.github.electroway.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddReviewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_review, container, false)
    }

    private val args: AddReviewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = (requireActivity().application as Application).session

        view.findViewById<Button>(R.id.submitReviewButton).setOnClickListener {
            val text = view.findViewById<TextView>(R.id.newReviewText).text.toString()
            val rating = (view.findViewById<RatingBar>(R.id.newReviewRating).rating * 2f).toInt()
            session.addReview(args.station, AddReviewInfo(rating, text)) {
                if (it) {
                    val action =
                        AddReviewFragmentDirections.actionAddReviewFragmentToReviewsFragment(args.station)
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Failed to add review", Toast.LENGTH_SHORT)
                }
            }
        }

        view.findViewById<ImageButton>(R.id.addReviewBackButton).setOnClickListener {
            val action = AddReviewFragmentDirections.actionAddReviewFragmentToReviewsFragment(args.station)
            findNavController().navigate(action)
        }
    }
}