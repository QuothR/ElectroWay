package com.github.electroway.ui.stations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.models.AddStation
import com.github.electroway.Application
import com.github.electroway.models.GeocoderAddress
import com.github.electroway.R
import com.github.electroway.ui.main.HomeFragment
import com.github.electroway.ui.main.HomeFragmentArgs
import com.github.electroway.ui.main.HomeFragmentDirections

class StationListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_station_list, container, false)
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
        val adapter = StationListAdapter(mutableListOf()) {
            val action = HomeFragmentDirections.actionHomeFragmentToStationFragment(it)
            homeFragment.findNavController().navigate(action)
        }
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        val geocoderAddress = GeocoderAddress(requireContext())

        session.getStations {
            if (it != null) {
                for (i in 0 until it.length()) {
                    val obj = it.getJSONObject(i)
                    val address = obj.getString("address")
                    val index = obj.getInt("id")
                    adapter.add(index, address)
                }
                adapter.notifyDataSetChanged()
            }
        }

        val homeArgs: HomeFragmentArgs by homeFragment.navArgs()
        if (homeArgs.stationToAdd != null) {
            val latLng = homeArgs.stationToAdd!!
            val addressLine = geocoderAddress.getFromLatLng(latLng)
            session.addStation(AddStation(addressLine, latLng.latitude, latLng.longitude)) {
                if (it != null) {
                    adapter.add(it.getInt("id"), addressLine)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Failed to add station", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}