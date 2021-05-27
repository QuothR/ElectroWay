package com.github.electroway.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.models.Car
import com.github.electroway.Application
import com.github.electroway.R

class CarDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = (requireActivity().application as Application).session

        val recyclerView = view.findViewById<RecyclerView>(R.id.carsRecyclerView)
        val homeFragment = requireParentFragment().requireParentFragment() as HomeFragment
        val adapter = CarListAdapter(mutableListOf()) {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateCarFragment(it)
            homeFragment.findNavController().navigate(action)
        }
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        view.findViewById<Button>(R.id.addCarButton).setOnClickListener {
            homeFragment.findNavController().navigate(R.id.action_homeFragment_to_addCarFragment)
        }

        session.getCars {
            if (it != null) {
                for (i in 0 until it.length()) {
                    val obj = it.getJSONObject(i)
                    adapter.add(
                        Car(
                            obj.getInt("id"),
                            obj.getString("model"),
                            obj.getInt("year"),
                            obj.getDouble("batteryCapacity"),
                            obj.getDouble("chargingCapacity"),
                            obj.getDouble("vehicleMaxSpeed"),
                            obj.getDouble("auxiliaryKwh")
                        )
                    )
                }
                adapter.notifyDataSetChanged()
            }
        }
    }
}