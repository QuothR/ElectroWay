package com.github.electroway.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.electroway.R
import com.google.android.gms.maps.SupportMapFragment

class MapFragment : Fragment() {
    private var mapFragment: SupportMapFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
        }
        parentFragmentManager.beginTransaction().replace(R.id.map, mapFragment!!).commit()

        view.findViewById<Toolbar>(R.id.toolbar).setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home_button -> {
                    findNavController().navigate(R.id.action_mapFragment_to_homeFragment)
                    true
                }
            }
            true
        }
    }
}