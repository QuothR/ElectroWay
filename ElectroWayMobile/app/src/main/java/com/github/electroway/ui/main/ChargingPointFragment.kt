package com.github.electroway.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.Application
import com.github.electroway.ChargingPlugInfo
import com.github.electroway.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONObject

class ChargingPointFragment : Fragment() {
    private val args: ChargingPointFragmentArgs by navArgs()
    private lateinit var dialog: BottomSheetDialog
    private lateinit var adapter: ChargingPlugListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog = BottomSheetDialog(requireActivity())
        dialog.setContentView(inflater.inflate(R.layout.charging_plug_dialog, container, false))
        return inflater.inflate(R.layout.fragment_charging_point, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = (requireActivity().application as Application).session

        view.findViewById<Button>(R.id.removeChargingPointButton).setOnClickListener {
            session.removeChargingPoint(args.station, args.chargingPoint) {
                if (it) {
                    val action =
                        ChargingPointFragmentDirections.actionChargingPointFragmentToStationFragment(
                            args.station
                        )
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed to remove charging point",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

        view.findViewById<ImageButton>(R.id.chargingPointBackButton).setOnClickListener {
            val action =
                ChargingPointFragmentDirections.actionChargingPointFragmentToStationFragment(args.station)
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.addPlugButton).setOnClickListener {
            dialog.show()
            val connectorType = dialog.findViewById<TextView>(R.id.connectorTypeText)!!.text
            val priceKw = dialog.findViewById<TextView>(R.id.priceKwText)!!.text
            val chargingSpeedKw = dialog.findViewById<TextView>(R.id.chargingSpeedKwText)!!.text
            dialog.findViewById<Button>(R.id.confirmPlugButton)!!.setOnClickListener {
                session.addChargingPlug(
                    args.station, args.chargingPoint, ChargingPlugInfo(
                        null,
                        connectorType.toString(),
                        priceKw.toString().toDouble(),
                        chargingSpeedKw.toString().toDouble()
                    )
                ) {
                    addChargingPoint(it!!)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.plugRecyclerView)
        adapter = ChargingPlugListAdapter(mutableListOf()) {
            val action =
                ChargingPointFragmentDirections.actionChargingPointFragmentToChargingPlugFragment(
                    it,
                    args.station,
                    args.chargingPoint
                )
            findNavController().navigate(action)
        }
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        session.getChargingPlugs(args.station, args.chargingPoint) {
            if (it != null) {
                for (i in 0 until it.length()) {
                    addChargingPoint(it.getJSONObject(i))
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun addChargingPoint(obj: JSONObject) {
        val id = obj.getInt("id")
        val status = obj.getInt("status")
        val connectorType = obj.getString("connectorType")
        val priceKw = obj.getDouble("priceKw")
        val chargingSpeedKw = obj.getDouble("chargingSpeedKw")
        adapter.add(ChargingPlugInfo(id, connectorType, priceKw, chargingSpeedKw))
    }
}