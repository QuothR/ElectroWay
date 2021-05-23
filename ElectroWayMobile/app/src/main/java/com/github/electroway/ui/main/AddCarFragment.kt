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
import com.github.electroway.AddCarInfo
import com.github.electroway.Application
import com.github.electroway.R

class AddCarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = (requireActivity().application as Application).session

        view.findViewById<Button>(R.id.submitCarButton).setOnClickListener {
            val model = view.findViewById<EditText>(R.id.modelEditText).text.toString()
            val year =
                view.findViewById<EditText>(R.id.yearEditText).text.toString().toInt()
            val batteryCapacity =
                view.findViewById<EditText>(R.id.batteryCapacityEditText).text.toString().toDouble()
            val chargingCapacity =
                view.findViewById<EditText>(R.id.chargingCapacityEditText).text.toString().toDouble()
            val vehicleMaxSpeed =
                view.findViewById<EditText>(R.id.vehicleMaxSpeedEditText).text.toString().toDouble()
            val auxiliaryKwh =
                view.findViewById<EditText>(R.id.auxiliaryKwhEditText).text.toString().toDouble()
            session.addCar(
                AddCarInfo(
                    null,
                    model,
                    year,
                    batteryCapacity,
                    chargingCapacity,
                    vehicleMaxSpeed,
                    auxiliaryKwh
                )
            ) {
                if (it) {
                    findNavController().navigate(R.id.action_addCarFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "Failed to add car", Toast.LENGTH_SHORT)
                }
            }
        }

        view.findViewById<ImageButton>(R.id.addCarBackButton).setOnClickListener {
            findNavController().navigate(R.id.action_addCarFragment_to_homeFragment)
        }
    }
}