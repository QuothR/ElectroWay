package com.github.electroway.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.Application
import com.github.electroway.R
import com.google.android.gms.maps.model.LatLng

class FavouritesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.favouritesRecyclerView)

        val adapter = FavouritesAdapter() {
            val homeFragment = requireParentFragment().requireParentFragment() as HomeFragment
            val action = HomeFragmentDirections.actionHomeFragmentToMapFragment(false, it)
            homeFragment.findNavController().navigate(action)
        }
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val session = (requireActivity().application as Application).session
        session.getAllStations {
            if (it != null) {
                for (i in 0 until it.length()) {
                    val obj = it.getJSONObject(i)
                    val id = obj.getInt("id")
                    session.isFavourite(id) {
                        if (it != null) {
                            val address = obj.getString("address")
                            val latitude = obj.getDouble("latitude")
                            val longitude = obj.getDouble("longitude")
                            adapter.add(address, LatLng(latitude, longitude))
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Failed to get stations",
                    Toast.LENGTH_SHORT
                )
            }
        }
    }
}