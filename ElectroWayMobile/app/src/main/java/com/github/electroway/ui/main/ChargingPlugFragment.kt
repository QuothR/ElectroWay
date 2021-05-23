package com.github.electroway.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.electroway.Application
import com.github.electroway.ChargingPlugInfo
import com.github.electroway.R

class ChargingPlugFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_charging_plug, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args: ChargingPlugFragmentArgs by navArgs()

        super.onViewCreated(view, savedInstanceState)

        val session = (requireActivity().application as Application).session

        val status = view.findViewById<EditText>(R.id.updatePlugStatusEditText)
        val connectorType = view.findViewById<EditText>(R.id.updatePlugConnectorTypeEditExt)
        val priceKw = view.findViewById<EditText>(R.id.updatePlugPriceKwEditText)
        val chargingSpeedKw = view.findViewById<EditText>(R.id.updatePlugChargingSpeedKwEditText)

        status.text.append(args.plug.status.toString())
        connectorType.text.append(args.plug.connectorType)
        priceKw.text.append(args.plug.priceKw.toString())
        chargingSpeedKw.text.append(args.plug.chargingSpeedKw.toString())

        view.findViewById<Button>(R.id.updateChargingPlugButton).setOnClickListener {
            session.updateChargingPlug(
                args.station, args.chargingPoint,
                ChargingPlugInfo(
                    args.plug.id,
                    status.text.toString().toInt(),
                    connectorType.text.toString(),
                    priceKw.text.toString().toDouble(),
                    chargingSpeedKw.text.toString().toDouble()
                )
            ) {
                if (it) {
                    val action =
                        ChargingPlugFragmentDirections.actionChargingPlugFragmentToChargingPointFragment(
                            args.station,
                            args.chargingPoint
                        )
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Failed update add charging plug",
                        Toast.LENGTH_SHORT
                    )
                }
            }
        }

        view.findViewById<ImageButton>(R.id.chargingPlugBackButton).setOnClickListener {
            val action =
                ChargingPlugFragmentDirections.actionChargingPlugFragmentToChargingPointFragment(
                    args.station,
                    args.chargingPoint
                )
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.removeChargingPlugButton).setOnClickListener {
            session.removeChargingPlug(args.station, args.chargingPoint, args.plug.id!!) {
                if (it) {
                    val action =
                        ChargingPlugFragmentDirections.actionChargingPlugFragmentToChargingPointFragment(
                            args.station,
                            args.chargingPoint
                        )
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Failed to remove charging plug", Toast.LENGTH_SHORT)
                }
            }
        }
    }
}