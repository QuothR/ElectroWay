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
import com.github.electroway.AddCarInfo
import com.github.electroway.Application
import com.github.electroway.R

class UpdateCarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args: UpdateCarFragmentArgs by navArgs()

        super.onViewCreated(view, savedInstanceState)

        val session = (requireActivity().application as Application).session

        val model = view.findViewById<EditText>(R.id.modelEditText)
        val year =
            view.findViewById<EditText>(R.id.yearEditText)
        val batteryCapacity =
            view.findViewById<EditText>(R.id.batteryCapacityEditText)
        val chargingCapacity =
            view.findViewById<EditText>(R.id.chargingCapacityEditText)
        val vehicleMaxSpeed =
            view.findViewById<EditText>(R.id.vehicleMaxSpeedEditText)
        val auxiliaryKwh =
            view.findViewById<EditText>(R.id.auxiliaryKwhEditText)

        model.text.append(args.car.model)
        year.text.append(args.car.year.toString())
        batteryCapacity.text.append(args.car.batteryCapacity.toString())
        chargingCapacity.text.append(args.car.chargingCapacity.toString())
        vehicleMaxSpeed.text.append(args.car.vehicleMaxSpeed.toString())
        auxiliaryKwh.text.append(args.car.auxiliaryKwh.toString())

        view.findViewById<Button>(R.id.updateCarButton).setOnClickListener {
            session.updateCar(
                AddCarInfo(
                    args.car.id,
                    model.text.toString(),
                    year.text.toString().toInt(),
                    batteryCapacity.text.toString().toDouble(),
                    chargingCapacity.text.toString().toDouble(),
                    vehicleMaxSpeed.text.toString().toDouble(),
                    auxiliaryKwh.text.toString().toDouble()
                )
            ) {
                if (it) {
                    findNavController().navigate(R.id.action_updateCarFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "Failed update add car", Toast.LENGTH_SHORT)
                }
            }
        }

        view.findViewById<ImageButton>(R.id.updateCarBackButton).setOnClickListener {
            findNavController().navigate(R.id.action_updateCarFragment_to_homeFragment)
        }

        view.findViewById<Button>(R.id.removeCarButton).setOnClickListener {
            session.removeCar(args.car.id!!) {
                if (it) {
                    findNavController().navigate(R.id.action_updateCarFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "Failed to remove car", Toast.LENGTH_SHORT)
                }
            }
        }
    }
}