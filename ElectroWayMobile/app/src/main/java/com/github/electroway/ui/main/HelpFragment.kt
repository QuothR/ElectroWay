package com.github.electroway.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.R

class HelpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.helpRecyclerView)
        var items = listOf(
            Pair("""
                What is Electroway?
            """.trimIndent(),
            """
                It’s an app designed for drivers of electric cars to find electric charging station. It shows:
                •  Shows locations on map
                •  You can set details of your car
                •  You’ll get stations based on your car’s needs and location
            """.trimIndent()),
            Pair("""
                How do I contact Electroway Customer Support?
            """.trimIndent(),
            """
                You can contact us at ….. for any problems.
            """.trimIndent()),
            Pair("""
                How do I validate my account?
            """.trimIndent(),
            """
                After registration you will get through email a link which will activate your account
            """.trimIndent()),
            Pair("""
                How many cars can I add?
            """.trimIndent(),
            """
                You have to add at least one car so that you can use the app.
            """.trimIndent()),
            Pair("""
                How many stations can I add?
            """.trimIndent(),
            """
                There is not a limit for the number of stations added.
            """.trimIndent()),
            Pair("""
                How do I get a copy of my supply receipt?
            """.trimIndent(),
            """
                You can find all your receipts at our website https://.......... after signing into your account at Statistics.
            """.trimIndent())
        )
        val adapter = HelpAdapter(items)
        val layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        view.findViewById<ImageButton>(R.id.helpBackButton).setOnClickListener {
            findNavController().navigate(R.id.action_helpFragment_to_mapFragment)
        }
    }
}