package com.github.electroway.ui.main

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.AddStationInfo
import com.github.electroway.Application
import com.github.electroway.R

class StationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeFragment = requireParentFragment().requireParentFragment() as HomeFragment

        view.findViewById<Button>(R.id.add_station_button).setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToMapFragment(true)
            homeFragment.findNavController().navigate(action)
        }

        val session = (requireActivity().application as Application).session
        val recyclerView = view.findViewById<RecyclerView>(R.id.stationsRecyclerView)
        val adapter = StationsAdapter(mutableListOf())
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        val geocoder = Geocoder(requireContext())

        val homeArgs: HomeFragmentArgs by homeFragment.navArgs()
        if (homeArgs.stationToAdd != null) {
            val latLng = homeArgs.stationToAdd!!
            val address =
                geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)[0]
            val addressLine =
                if (address != null) address.getAddressLine(0) else "${latLng.latitude}, ${latLng.longitude}"
            session.addStation(AddStationInfo(addressLine, latLng.latitude, latLng.longitude)) {
                if (it) {
                    adapter.add(addressLine)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Failed to add station", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        session.getStations {
            if (it != null) {
                for (i in 0 until it.length()) {
                    val obj = it.getJSONObject(i)
                    val address = obj.getString("address")
                    adapter.add(address)
                }
                adapter.notifyDataSetChanged()
            }
        }
    }
}