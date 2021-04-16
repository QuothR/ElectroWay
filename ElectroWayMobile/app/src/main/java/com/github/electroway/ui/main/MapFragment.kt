package com.github.electroway.ui.main

import android.app.SearchManager
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.electroway.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.random.Random

class MapFragment : Fragment(), OnMapReadyCallback {
    private var mapFragment: SupportMapFragment? = null
    private val stations = List(Random.nextInt(1000)) { i ->
        val location = LatLng(
            Random.Default.nextDouble(-90.0, 90.0),
            Random.Default.nextDouble(-180.0, 180.0)
        )
        Pair("Station $i", location)
    }
    private var map: GoogleMap? = null

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
        mapFragment!!.getMapAsync(this)

        view.findViewById<Toolbar>(R.id.toolbar).setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home_button -> {
                    findNavController().navigate(R.id.action_mapFragment_to_homeFragment)
                    true
                }
            }
            true
        }
        val searchView =
            view.findViewById<Toolbar>(R.id.toolbar).menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.queryHint = R.string.search.toString()
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = IntArray(1) { android.R.id.text1 }
        val cursorAdapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        searchView.suggestionsAdapter = cursorAdapter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val cursor =
                    MatrixCursor(
                        arrayOf(
                            BaseColumns._ID,
                            SearchManager.SUGGEST_COLUMN_TEXT_1
                        )
                    )
                query!!.let {
                    stations.forEachIndexed { index, suggestion ->
                        if (suggestion.first.contains(query, true)) {
                            cursor.addRow(arrayOf(index, suggestion.first))
                        }
                    }
                }
                searchView.suggestionsAdapter.changeCursor(cursor)
                return true
            }
        })
        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                hideKeyboard()
                val location = stations.get(position).second
                map?.moveCamera(CameraUpdateFactory.newLatLng(location))
                return true
            }
        })
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        stations.forEach { station ->
            map!!.addMarker(MarkerOptions().position(station.second).title(station.first))
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}