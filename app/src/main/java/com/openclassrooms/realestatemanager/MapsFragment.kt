package com.openclassrooms.realestatemanager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.util.ObjectsCompat.requireNonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.common.internal.Objects
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.models.RealtyItem
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory
import java.io.IOException
import java.util.Objects.requireNonNull
import java.util.concurrent.TimeUnit


class MapsFragment : Fragment() {

    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory(
            (activity?.application as RealStateManagerApplication).repository,
            (activity?.application as RealStateManagerApplication).repositoryShot
        )
    }
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    // LocationCallback - Called when FusedLocationProviderClient has a new Location.
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: MutableLiveData<Location?> = MutableLiveData(null)
  //  private var currentLocation: Location? = null
    private val callback = OnMapReadyCallback { googleMap ->
        myViewModel.getAllRealtyItem.observe(this){
            currentLocation.observe(this, Observer {
                var latlngcurrent = it?.let { it1 ->
                    LatLng(it1.latitude, it1.longitude)
                }
                if (latlngcurrent != null) {
                    println(" current location"+latlngcurrent.toString())
                    val markerOptions=MarkerOptions().position(latlngcurrent).title("Current Location")
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    googleMap.addMarker(markerOptions)
                  // googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlngcurrent))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngcurrent, 12F))
                }
                else{
                    println("null current location")
                }
            })
           addRealtyInMaps(it,googleMap)
        }
      //show Zoom Controller
      googleMap.uiSettings.isZoomControlsEnabled=true
    }

    private fun addRealtyInMaps(listRealty: List<RealtyItem>, googleMap: GoogleMap) {
        var size=listRealty.size
        size--
        for(i in 0..size){
            var adress=listRealty[i].address
            var type=listRealty[i].type
            var latLng= getLatLngFromAdresse(adress)
           // println("id $idRealty")
            latLng.let {
                var latLngRealty = LatLng(it!!.latitude, it!!.longitude)
                var markerOption=MarkerOptions().position(latLngRealty).title(type)
           //   googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLngRealty))
                var marker: Marker? = googleMap.addMarker(markerOption)
                 marker?.tag = listRealty[i]
                googleMap.setOnMarkerClickListener {
                    println("tags    is  ${(it?.tag as RealtyItem).idRealty}")
                  var realty=it?.tag  as RealtyItem
                    var idRealty=realty.idRealty
                    var intent = Intent(activity, DetailActivity::class.java)
                   println("id  in setOnMarkerClickListener is  $idRealty")
                    intent.putExtra(KEY_ID_REALTY,idRealty )
                    startActivity(intent)
                    true
                }

            }

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        val myToolbar = view.findViewById<Toolbar>(R.id.myToolbar3)
        myToolbar.title = "Maps Fragment"
        myToolbar?.setNavigationOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_mapsFragment_to_mainFragment2)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(requireContext())
        getCurrentLocation()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
    fun getLatLngFromAdresse(adresse: String): LatLng? {
        val geocoder = Geocoder(context)
         var latlng: LatLng? =null
        try {
            val adr: List<Address> = geocoder.getFromLocationName(adresse, 1)
            if(adr.isNotEmpty()) {
                val lat: Double = adr[0].getLatitude()
                val lng: Double = adr[0].getLongitude()
                latlng = LatLng(lat, lng)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return latlng
    }
    private fun getCurrentLocation() {
        if(checkPermission()){
            if(isLocationEnabled()){
                //get the current Location
                locationRequest = LocationRequest.create().apply {
                    interval = TimeUnit.SECONDS.toMillis(60)
                    fastestInterval = TimeUnit.SECONDS.toMillis(30)
                    maxWaitTime = TimeUnit.MINUTES.toMillis(2)
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                locationCallback= object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        if (locationRequest == null) {
                            Log.e("currentlocation", "locationResult is null")
                        }
                        for (location in locationResult.locations) {
                            if (location != null) {
                                currentLocation.postValue(location)
                              //  currentLocation=location
                                Log.e("current Location", "current Location " + location.longitude + " Latitude " + location.latitude)
                            }
                            else{
                                Log.e("current Location", "is null")

                            }
                        }
                    }
                }

                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,  Looper.myLooper())
                val task = fusedLocationProviderClient.lastLocation
                task.addOnSuccessListener { location ->
                    if (location != null) {
                       currentLocation.postValue(location)
                       // currentLocation=location
                        println("getLatitude=$location")
                      //  mCurrentLocationText.text = currentLocation?.latitude.toString() + "," + currentLocation?.longitude.toString()
                    }
                    else{
                        println("lastLocation is null") }
                }


                /***/
            }
            else{
                //setting open here
                Toast.makeText(context,"Turn on  location ", Toast.LENGTH_SHORT).show()
                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else{
            //request permission here
            requestPermission()
        }
    }
    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION),PERMISSION_REQUEST_ACCESS_LOCATION)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER )|| locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)

    }


    private fun checkPermission():Boolean{
        return (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

    }

     override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== PERMISSION_REQUEST_ACCESS_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(context,"Permission granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            }
            else{
                Toast.makeText(context,"Permission not granted ", Toast.LENGTH_SHORT).show()
            }

        }
    }


}