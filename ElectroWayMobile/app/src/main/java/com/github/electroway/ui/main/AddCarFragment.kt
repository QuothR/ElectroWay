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
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.CarInfo
import com.github.electroway.Application
import com.github.electroway.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddCarFragment : Fragment() {
    private lateinit var templateDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        templateDialog = BottomSheetDialog(requireActivity())
        templateDialog.setContentView(
            inflater.inflate(
                R.layout.template_car_dialog,
                container,
                false
            )
        )
        return inflater.inflate(R.layout.fragment_add_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = (requireActivity().application as Application).session

        val model = view.findViewById<EditText>(R.id.modelEditText).text
        val year = view.findViewById<EditText>(R.id.yearEditText).text
        val batteryCapacity = view.findViewById<EditText>(R.id.batteryCapacityEditText).text
        val chargingCapacity = view.findViewById<EditText>(R.id.chargingCapacityEditText).text
        val vehicleMaxSpeed = view.findViewById<EditText>(R.id.vehicleMaxSpeedEditText).text
        val auxiliaryKwh = view.findViewById<EditText>(R.id.auxiliaryKwhEditText).text

        view.findViewById<Button>(R.id.submitCarButton).setOnClickListener {
            session.addCar(
                CarInfo(
                    null,
                    model.toString(),
                    year.toString().toInt(),
                    batteryCapacity.toString().toDouble(),
                    chargingCapacity.toString().toDouble(),
                    vehicleMaxSpeed.toString().toDouble(),
                    auxiliaryKwh.toString().toDouble(),
                )
            ) {
                if (it) {
                    findNavController().navigate(R.id.action_addCarFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "Failed to add car", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val recyclerView =
            templateDialog.findViewById<RecyclerView>(R.id.templateCarsRecyclerView)!!
        val adapter = TemplateCarListAdapter(mutableListOf()) {
            model.clear(); model.append(it.model)
            year.clear(); year.append(it.year.toString())
            batteryCapacity.clear(); batteryCapacity.append(it.batteryCapacity.toString())
            chargingCapacity.clear(); chargingCapacity.append(it.chargingCapacity.toString())
            vehicleMaxSpeed.clear(); vehicleMaxSpeed.append(it.vehicleMaxSpeed.toString())
            auxiliaryKwh.clear(); auxiliaryKwh.append(it.auxiliaryKwh.toString())
            templateDialog.hide()
        }
        recyclerView.adapter = adapter

        session.getTemplateCars {
            if (it != null) {
                for (i in 0 until it.length()) {
                    val template = it.getJSONObject(i)
                    val model = template.getString("model")
                    val year = template.getInt("year")
                    val batteryCapacity = template.getDouble("batteryCapacity")
                    val chargingCapacity = template.getDouble("chargingCapacity")
                    val vehicleMaxSpeed = template.getDouble("vehicleMaxSpeed")
                    val auxiliaryKwh = template.getDouble("auxiliaryKwh")
                    adapter.add(
                        CarInfo(
                            null,
                            model,
                            year,
                            batteryCapacity,
                            chargingCapacity,
                            vehicleMaxSpeed,
                            auxiliaryKwh
                        )
                    )
                }
            }
        }

        view.findViewById<Button>(R.id.templateCarButton).setOnClickListener {
            templateDialog.show()
        }

        view.findViewById<ImageButton>(R.id.addCarBackButton).setOnClickListener {
            findNavController().navigate(R.id.action_addCarFragment_to_homeFragment)
        }
    }
}