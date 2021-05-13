package com.github.electroway.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.github.electroway.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private val args: HomeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navView = view.findViewById<BottomNavigationView>(R.id.home_bottom_nav_view)
        navView.setupWithNavController(requireActivity().findNavController(R.id.home_fragment_container_view))

        view.findViewById<ImageButton>(R.id.homeBackButton).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }

        if (args.stationToAdd != null) {
            requireActivity().findNavController(R.id.home_fragment_container_view)
                .navigate(R.id.action_navigation_car_details_to_navigation_stations)
        }
    }

    fun navigateToSignIn() {
        findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
    }
}