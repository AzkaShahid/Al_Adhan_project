package com.app.ui.sidenavigation.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.R
import com.app.databinding.FragmentHomeBinding
import com.app.bases.BaseFragment
import com.app.preferences.PreferenceUtility
import com.app.utils.TrafficConditionEvaluator
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsResult
import com.google.maps.model.DirectionsRoute
import com.google.maps.model.TrafficModel
import com.google.maps.model.TravelMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private var currentLocation: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var googleMap: GoogleMap? = null
    private var selectedRoutes: MutableList<Polyline> = mutableListOf()
    private val destinationLatLng = LatLng(29.3944299, 71.662356) // Example destination coordinates
    //private val trafficEvaluator = TrafficConditionEvaluator()

    override val mViewModel: HomeViewModel by viewModels()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun getToolbarBinding() = null

    override fun getToolbarTitle() = R.string.menu_home

    override fun isMenuButton() = true

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setupUI(savedInstanceState: Bundle?) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mViewBinding.getCurrentLocation.setOnClickListener {
            getCurrentLocation()
        }

        mViewBinding.btnGetRoutes.setOnClickListener {
            getRoutes(currentLocation ?: return@setOnClickListener, destinationLatLng)
        }

        mViewBinding.mapView.onCreate(savedInstanceState)

        val spinnerItems = resources.getStringArray(R.array.spinner_items)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mViewBinding.spinner.adapter = adapter

        // Handle spinner item selection
        mViewBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                // Handle selected item
                handleSpinnerSelectedItem(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        // Check and request location permission
        if (hasLocationPermission()) {
            loadMap()
        } else {
            requestLocationPermission()
        }
    }

    private fun handleSpinnerSelectedItem(item: String) {
        // Example: Display item's text in a toast
        Toast.makeText(requireContext(), "Selected Item: $item", Toast.LENGTH_SHORT).show()
    }
    override fun attachListener() {
    }

    override fun observeViewModel() {
    }

    private fun loadMap() {
        try {
            MapsInitializer.initialize(requireContext())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mViewBinding.mapView.getMapAsync(OnMapReadyCallback { mMap ->
            googleMap = mMap

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                googleMap?.isMyLocationEnabled = true
                // Get the current location
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        updateMapWithLocation(location)
                    } else {
                        Log.e("loadMap", "Current location is null")
                    }
                }.addOnFailureListener { exception ->
                    Log.e("loadMap", "Error getting location: ${exception.message}")
                }
            } else {
                // Request location permission
                requestLocationPermission()
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun updateMapWithLocation(location: Location) {
        currentLocation = LatLng(location.latitude, location.longitude)
        googleMap?.apply {
            clear()
            addMarker(
                MarkerOptions().position(currentLocation!!).title("Your Location")
                    .icon(
                        PreferenceUtility.bitmapDescriptorFromVector(
                            requireContext(),
                            R.drawable.ic_map_marker
                        )
                    )
            )
            val cameraPosition = CameraPosition.Builder().target(currentLocation!!).zoom(16f).build()
            animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    private fun getRoutes(origin: LatLng, destination: LatLng) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val context = GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_key))
                    .build()

                val result: DirectionsResult = DirectionsApi.newRequest(context)
                    .mode(TravelMode.DRIVING)
                    .origin("${origin.latitude},${origin.longitude}")
                    .destination("${destination.latitude},${destination.longitude}")
                    .departureTimeNow() // Requests traffic data for the current time
                    .trafficModel(TrafficModel.BEST_GUESS) // You can change this to PESSIMISTIC or OPTIMISTIC
                    .alternatives(true)
                    .await()

                withContext(Dispatchers.Main) {
                    displayRoutes(result)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error fetching routes: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun displayRoutes(result: DirectionsResult) {
        googleMap?.clear()
        selectedRoutes.forEach { it.remove() } // Clear previous routes
        selectedRoutes.clear()

        for (route in result.routes) {
            val decodedPath = PolylineOptions().apply {
                color(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                width(10f)
                route.overviewPolyline.decodePath().forEach {
                    add(LatLng(it.lat, it.lng)) // Use com.google.android.gms.maps.model.LatLng here
                }
            }
            val polyline = googleMap?.addPolyline(decodedPath)
            polyline?.tag = route
            polyline?.isClickable = true
            polyline?.let { selectedRoutes.add(it)
            }
        }

        // Set up polyline click listener
        googleMap?.setOnPolylineClickListener { polyline ->
            val route = polyline.tag as DirectionsRoute
            val distanceInMeters = route.legs.sumOf { it.distance.inMeters }
            val distanceInKm = distanceInMeters.toDouble() / 1000.0 // Convert meters to kilometers
            val roadName = route.summary
            // Show custom popup message
            showPopupMessage(route, currentLocation!!, distanceInKm, roadName)
        }
    }

//    private fun showPopupMessage(route: DirectionsRoute, distance: Double, roadName: String) {
//        val dialogView = layoutInflater.inflate(R.layout.popup_message, null)
//        val tvDistance = dialogView.findViewById<TextView>(R.id.tvDistance)
//        val tvRoadName = dialogView.findViewById<TextView>(R.id.tvRoadName)
//        val tvTrafficCondition = dialogView.findViewById<TextView>(R.id.tvTrafficCondition)
//        val btnOK = dialogView.findViewById<Button>(R.id.btnOK)
//
//        val durationInTraffic = route.legs.sumOf { it.durationInTraffic.inSeconds }
//        val durationInTrafficMinutes = durationInTraffic.toDouble() / 60.0
//        val normalDurationMinutes = route.legs.sumOf { it.duration.inSeconds }.toDouble() / 60.0
//
//        // Determine traffic condition
//        val trafficCondition = when {
//            durationInTrafficMinutes > normalDurationMinutes * 1.5 -> "Heavy Traffic"
//            durationInTrafficMinutes > normalDurationMinutes * 1.2 -> "Abnormal Traffic"
//            else -> "Normal Traffic"
//        }
//
//        tvDistance.text = "Distance: ${String.format("%.2f km", distance)}"
//        tvRoadName.text = "Road Name: $roadName"
//        tvTrafficCondition.text = "Traffic Condition: $trafficCondition"
//
//        val builder = AlertDialog.Builder(requireContext())
//            .setView(dialogView)
//            .setCancelable(true)
//
//        val dialog = builder.create()
//        dialog.show()
//
//        btnOK.setOnClickListener {
//            dialog.dismiss()
//        }
//    }

    private fun showPopupMessage(route: DirectionsRoute, currentLocation: LatLng, distance: Double, roadName: String) {
        val dialogView = layoutInflater.inflate(R.layout.popup_message, null)
        val tvDistance = dialogView.findViewById<TextView>(R.id.tvDistance)
        val tvRoadName = dialogView.findViewById<TextView>(R.id.tvRoadName)
        val tvTrafficCondition = dialogView.findViewById<TextView>(R.id.tvTrafficCondition)
        val btnOK = dialogView.findViewById<Button>(R.id.btnOK)

        // Assume you get the following values from sensors
        val speed = getSpeedForRoute(route) // Replace with actual sensor data
        val density = getDensityForRoute(route) // Replace with actual sensor data
        val motionDetected = isMotionDetectedForRoute(route) // Replace with actual sensor data

        val trafficConditionEvaluator = TrafficConditionEvaluator()
        val trafficCondition = trafficConditionEvaluator.evaluateTrafficCondition(speed, density, motionDetected)

        tvDistance.text = "Distance: ${String.format("%.2f km", distance)}"
        tvRoadName.text = "Road Name: $roadName"
        tvTrafficCondition.text = "Traffic Condition: $trafficCondition"

        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)

        val dialog = builder.create()
        dialog.show()

        btnOK.setOnClickListener {
            dialog.dismiss()
        }
    }

    // Dummy functions to represent sensor data retrieval
    private fun getSpeedForRoute(route: DirectionsRoute): Double {
        // Replace with actual logic to get speed from sensors
        return 45.0
    }

    private fun getDensityForRoute(route: DirectionsRoute): Double {
        // Replace with actual logic to get density from sensors
        return 15.0
    }

    private fun isMotionDetectedForRoute(route: DirectionsRoute): Boolean {
        // Replace with actual logic to detect motion from sensors
        return true
    }


    private fun getCurrentLocation() {
        if (hasLocationPermission()) {
            requestLocation()
        } else {
            requestLocationPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    updateMapWithLocation(it)
                } ?: run {
                    Toast.makeText(requireContext(), "Failed to get current location.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to get current location: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadMap()
            } else {
                Toast.makeText(requireContext(), "Location permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mViewBinding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mViewBinding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mViewBinding.mapView.onLowMemory()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}
