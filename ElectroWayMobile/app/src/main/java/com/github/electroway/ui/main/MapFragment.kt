package com.github.electroway.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.github.electroway.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject

class MapFragment : Fragment(), OnMapReadyCallback {
    private var mapFragment: SupportMapFragment? = null
    private lateinit var googleMap: GoogleMap
    private lateinit var markerBitmap: BitmapDescriptor
    private lateinit var key: String

    companion object {
        const val AUTOCOMPLETE_REQUEST_CODE = 1
    }

    private val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
    private val args: MapFragmentArgs by navArgs()
    private lateinit var locationDialog: BottomSheetDialog
    private lateinit var routeDialog: BottomSheetDialog
    private lateinit var waypointsDialog: BottomSheetDialog
    private lateinit var stationDialog: BottomSheetDialog
    private lateinit var payDialog: BottomSheetDialog
    private val cars = mutableMapOf<String, Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationDialog = BottomSheetDialog(requireActivity())
        locationDialog.setContentView(
            inflater.inflate(
                R.layout.map_dialog,
                container,
                false
            )
        )
        routeDialog = BottomSheetDialog(requireActivity())
        routeDialog.setContentView(
            inflater.inflate(
                R.layout.route_configuration_dialog,
                container,
                false
            )
        )
        waypointsDialog = BottomSheetDialog(requireActivity())
        waypointsDialog.setContentView(
            inflater.inflate(
                R.layout.waypoints_dialog,
                container,
                false
            )
        )
        stationDialog = BottomSheetDialog(requireActivity())
        stationDialog.setContentView(
            inflater.inflate(
                R.layout.station_dialog,
                container,
                false
            )
        )
        payDialog = BottomSheetDialog(requireActivity())
        payDialog.setContentView(
            inflater.inflate(
                R.layout.pay_dialog,
                container,
                false
            )
        )
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
        }
        parentFragmentManager.beginTransaction().replace(R.id.map, mapFragment!!).commit()
        mapFragment!!.getMapAsync(this)

        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(requireContext())

        view.findViewById<ImageButton>(R.id.homeButton).setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_homeFragment)
        }
        view.findViewById<ImageButton>(R.id.searchButton).setOnClickListener {
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }
        view.findViewById<ImageButton>(R.id.helpButton).setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_helpFragment)
        }

        if (!Places.isInitialized()) {
            val context = requireContext()
            context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            ).apply {
                key = metaData.getString("com.google.android.geo.API_KEY")!!
                Places.initialize(requireActivity().applicationContext, key)
            }
        }

        view.findViewById<FloatingActionButton>(R.id.mapLocationButton)
            .setOnClickListener { moveToCurrentLocation() }

        val routeCarSpinner = routeDialog.findViewById<Spinner>(R.id.routeCarSpinner)!!
        val session = (requireActivity().application as Application).session
        session.getCars {
            if (it != null) {
                val rows = mutableListOf<String>()
                for (i in 0 until it.length()) {
                    val car = it.getJSONObject(i)
                    val id = car.getInt("id")
                    val model = car.getString("model")
                    val year = car.getInt("year")
                    val title = "$model, $year"
                    cars.put(title, id)
                    rows.add(title)
                }
                routeCarSpinner.adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, rows)
            }
        }
    }

    private fun getProviderAndLocationManager(): Pair<String?, LocationManager> {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 44
            )
        }
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var provider: String? = null
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER
        } else if (locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        ) {
            provider = LocationManager.NETWORK_PROVIDER
        }
        return Pair(provider, locationManager);
    }

    @SuppressLint("MissingPermission")
    private fun moveToCurrentLocation() {
        val (provider, locationManager) = getProviderAndLocationManager()
        if (provider != null) {
            locationManager.getCurrentLocation(
                provider,
                null,
                requireContext().mainExecutor
            ) {
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            it.latitude,
                            it.longitude
                        ), 20.0f
                    )
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                place.latLng,
                                20.0f
                            )
                        )
                    }
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        if (args.addStation) {
            googleMap.setOnMapLongClickListener {
                val action = MapFragmentDirections.actionMapFragmentToHomeFragment(it)
                findNavController().navigate(action)
            }
            requireView().findViewById<ImageButton>(R.id.homeButton).apply {
                isEnabled = false
                visibility = View.INVISIBLE
            }
        } else {
            if (args.show != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(args.show, 20.0f))
            }
            val session = (requireActivity().application as Application).session
            val stations = mutableMapOf<Int, String>()
            session.getAllStations {
                if (it != null) {
                    markerBitmap =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_baseline_ev_station
                        )!!.run {
                            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                            val bitmap =
                                Bitmap.createBitmap(
                                    intrinsicWidth,
                                    intrinsicHeight,
                                    Bitmap.Config.ARGB_8888
                                )
                            draw(Canvas(bitmap))
                            BitmapDescriptorFactory.fromBitmap(bitmap)
                        }
                    val markersPos = mutableMapOf<Marker, Int>()
                    for (i in 0 until it.length()) {
                        val obj = it.getJSONObject(i)
                        val address = obj.getString("address")
                        val latitude = obj.getDouble("latitude")
                        val longitude = obj.getDouble("longitude")
                        val id = obj.getInt("id")
                        session.isFavourite(id) {
                            if (it != null) {
                                markersPos[googleMap.addMarker(
                                    MarkerOptions().position(
                                        LatLng(
                                            latitude,
                                            longitude
                                        )
                                    ).title(address)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.favourite_mark))
                                )] = id
                            } else {
                                markersPos[googleMap.addMarker(
                                    MarkerOptions().position(LatLng(latitude, longitude))
                                        .title(address)
                                        .icon(markerBitmap)
                                )] = id
                            }
                        }
                    }

                    googleMap.setOnInfoWindowClickListener { marker ->
                        stationDialog.show()
                        val showReviewsButton =
                            stationDialog.findViewById<Button>(R.id.showReviewsButton)!!
                        val removeFavouriteButton =
                            stationDialog.findViewById<Button>(R.id.removeFavouriteButton)!!
                        showReviewsButton.setOnClickListener {
                            stationDialog.hide()
                            if (markersPos[marker] != null) {
                                val action =
                                    MapFragmentDirections.actionMapFragmentToReviewsFragment(
                                        markersPos[marker]!!
                                    )
                                findNavController().navigate(action)
                            }
                        }
                        session.isFavourite(markersPos[marker]!!) { favourite ->
                            val favouriteButton =
                                stationDialog.findViewById<Button>(R.id.favouriteButton)!!
                            if (favourite == null) {
                                favouriteButton.visibility = View.VISIBLE
                                removeFavouriteButton.visibility = View.GONE
                                favouriteButton.setOnClickListener {
                                    stationDialog.hide()
                                    if (markersPos[marker] != null) {
                                        session.createFavourite(markersPos[marker]!!) {
                                            if (it) {
                                                marker.setIcon(
                                                    BitmapDescriptorFactory.fromResource(
                                                        R.drawable.favourite_mark
                                                    )
                                                )
                                                val info = markersPos.remove(marker)
                                                markersPos[marker] = info!!
                                            }
                                        }
                                    }
                                }
                            } else {
                                favouriteButton.visibility = View.GONE
                                removeFavouriteButton.visibility = View.VISIBLE
                                removeFavouriteButton.setOnClickListener {
                                    stationDialog.hide()
                                    if (markersPos[marker] != null) {
                                        session.deleteFavourite(markersPos[marker]!!, favourite) {
                                            if (it) {
                                                marker.setIcon(markerBitmap)
                                                val info = markersPos.remove(marker)
                                                markersPos[marker] = info!!
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        stationDialog.findViewById<Button>(R.id.stationPayButton)!!.setOnClickListener {
                            stationDialog.hide()
                            payDialog.show()
                            payDialog.findViewById<Button>(R.id.paySubmitButton)!!.setOnClickListener {
                                val point = payDialog.findViewById<EditText>(R.id.payChargingPointText)!!.text.toString().toInt()
                                val plug = payDialog.findViewById<EditText>(R.id.payChargingPlugText)!!.text.toString().toInt()
                                val totalKw = payDialog.findViewById<EditText>(R.id.payTotalKwText)!!.text.toString().toInt()
                                session.pay(markersPos[marker]!!, point, plug, totalKw) {
                                    if (it != null) {
                                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                                        payDialog.hide()
                                    } else {
                                        Toast.makeText(requireContext(), "Failed to pay", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            googleMap.setOnMapLongClickListener { latLng ->
                locationDialog.show()
                locationDialog.findViewById<Button>(R.id.findRouteButton)!!.setOnClickListener {
                    locationDialog.hide()
                    routeDialog.show()
                    val routeCarSpinner = routeDialog.findViewById<Spinner>(R.id.routeCarSpinner)
                    val currentChargeInkWText =
                        routeDialog.findViewById<EditText>(R.id.currentChargeInkWText)
                    routeDialog.findViewById<Button>(R.id.routeConfigurationFindButton)!!
                        .setOnClickListener {
                            routeDialog.hide()
                            val (provider, locationManager) = getProviderAndLocationManager()
                            if (provider != null) {
                                locationManager.getCurrentLocation(
                                    provider,
                                    null,
                                    requireContext().mainExecutor
                                ) {
                                    val origin = LatLng(it.latitude, it.longitude)
                                    val destination = latLng
                                    session.findRoute(
                                        FindRouteInfo(
                                            arrayOf(
                                                origin,
                                                destination
                                                /*LatLng(44.18508, 28.51904),
                                                LatLng(46.21379, 24.7817)*/
                                            ),
                                            cars[routeCarSpinner!!.selectedItem.toString()]!!,
                                            currentChargeInkWText!!.text.toString().toDouble()
                                        )
                                    ) {
                                        Log.e("a", it.toString())
                                        if (it != null) {
                                            drawRoute(googleMap, stations, it)
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
    }

    private fun drawRoute(
        googleMap: GoogleMap,
        stations: MutableMap<Int, String>,
        obj: JSONObject
    ) {
        val legs = obj.getJSONArray("legs");
        val polylineOptions = PolylineOptions().color(0xff726c91.toInt()).clickable(true)
        val adapter = WaypointListAdapter(mutableListOf())
        for (i in 0 until legs.length()) {
            val leg = legs.getJSONObject(i)
            val stationId = leg.optInt("stationId")
            if (stationId != 0) {
                val price = leg.getDouble("priceKw")
                val charge = leg.getDouble("currentChargeInkWhAfterRecharge")
                adapter.add("${stations[stationId]!!}, price (kW): $price, charge: $charge")
            }
            val points = leg.getJSONArray("points")
            for (j in 0 until points.length()) {
                val point = points.getJSONObject(j)
                polylineOptions.add(LatLng(point.getDouble("lat"), point.getDouble("lon")))
            }
        }
        googleMap.addPolyline(polylineOptions)
        googleMap.setOnPolylineClickListener {
            val waypointsRecyclerView =
                waypointsDialog.findViewById<RecyclerView>(R.id.waypointsRecyclerView)!!
            waypointsRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
            waypointsDialog.show()
        }
    }
}