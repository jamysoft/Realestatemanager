package com.openclassrooms.realestatemanager

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory
import java.io.IOException

class MapsActivity : AppCompatActivity() {
    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory(
            (application as RealStateManagerApplication).repository,
            (application as RealStateManagerApplication).repositoryShot
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
                var sydney = LatLng(getLatLngFromAdresse(it[i].address).latitude, getLatLngFromAdresse(it[i].address).longitude)
                googleMap.addMarker(MarkerOptions().position(sydney).title(title))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            }
        }
        val sydney = LatLng(-34.0, 151.0)

        //   googleMap.addMarker(MarkerOptions().position(paris).title("Marker in Paris"))
        //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(paris))
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sideny"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

    }
    fun getLatLngFromAdresse(adresse: String): LatLng {
        val geocoder = Geocoder(this)
        lateinit var latlng: LatLng
        try {
            val adr: List<Address> = geocoder.getFromLocationName(adresse, 1)
            val lat: Double = adr[0].getLatitude()
            val lng: Double = adr[0].getLongitude()
            latlng = LatLng(lat, lng)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return latlng
    }
}