package com.github.electroway.ui.main

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.Application
import com.github.electroway.R

class StationFragment : Fragment() {
    private val args: StationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_station, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = (requireActivity().application as Application).session

        view.findViewById<Button>(R.id.removeStationButton).setOnClickListener {
            session.removeStation(args.station) {
                if (it) {
                    findNavController().navigate(R.id.action_station_fragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "Failed to remove station", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        view.findViewById<ImageButton>(R.id.stationBackButton).setOnClickListener {
            findNavController().navigate(R.id.action_station_fragment_to_homeFragment)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.chargingPointRecyclerView)
        val adapter = ChargingPointListAdapter(mutableListOf()) {
            val action = StationFragmentDirections.actionStationFragmentToChargingPointFragment(args.station, it)
            findNavController().navigate(action)
        }
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        session.getChargingPoints(args.station) {
            if (it != null) {
                for (i in 0 until it.length()) {
                    val obj = it.getJSONObject(i)
                    val id = obj.getInt("id")
                    adapter.add(id)
                }
                adapter.notifyDataSetChanged()
            }
        }

        view.findViewById<Button>(R.id.addChargingPointButton).setOnClickListener {
            session.addChargingPoint(args.station) {
                if (it != null) {
                    adapter.add(it.getInt("id"))
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to add charging point",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}