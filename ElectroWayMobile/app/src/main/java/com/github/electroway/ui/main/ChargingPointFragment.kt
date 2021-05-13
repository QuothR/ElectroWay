package com.github.electroway.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.Application
import com.github.electroway.R

class ChargingPointFragment : Fragment() {
    private val args: ChargingPointFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_charging_point, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = (requireActivity().application as Application).session

        view.findViewById<Button>(R.id.removeChargingPointButton).setOnClickListener {
            session.removeChargingPoint(args.station, args.chargingPoint) {
                if (it) {
                    val action = ChargingPointFragmentDirections.actionChargingPointFragmentToStationFragment(args.station)
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Failed to remove charging point", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        view.findViewById<ImageButton>(R.id.chargingPointBackButton).setOnClickListener {
            val action = ChargingPointFragmentDirections.actionChargingPointFragmentToStationFragment(args.station)
            findNavController().navigate(action)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.plugRecyclerView)
        val adapter = ChargingPointListAdapter(mutableListOf()) {
        }
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        session.getPlugs(args.station, args.chargingPoint) {
            if (it != null) {
                for (i in 0 until it.length()) {
                    val obj = it.getJSONObject(i)
                    val id = obj.getInt("id")
                    adapter.add(id)
                }
                adapter.notifyDataSetChanged()
            }
        }
    }
}