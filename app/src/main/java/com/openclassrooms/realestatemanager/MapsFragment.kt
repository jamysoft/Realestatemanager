package com.openclassrooms.realestatemanager

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory
import java.io.IOException


class MapsFragment : Fragment() {

    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory(
            (activity?.application as RealStateManagerApplication).repository,
            (activity?.application as RealStateManagerApplication).repositoryShot
        )
    }

    private val callback = OnMapReadyCallback { googleMap ->
        myViewModel.getAllRealtyItem.observe(this){
           var size=it.size
            size--
            for(i in 0..size){
                println(it[i].address)
                getLatLngFromAdresse(it[i].address)
                var title=it[i].type
                getLatLngFromAdresse(it[i].address).let {
                    var sydney = LatLng(it!!.latitude, it!!.longitude)
                    googleMap.addMarker(MarkerOptions().position(sydney).title(title))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                }
            }
        }
        val sydney = LatLng(-34.0, 151.0)

        //   googleMap.addMarker(MarkerOptions().position(paris).title("Marker in Paris"))
        //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(paris))
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sideny"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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

}