package com.openclassrooms.realestatemanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private lateinit var mImageadapter: GalleryAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mImageView: ImageView
    private lateinit var mSurface: TextView
    private lateinit var mLocation: TextView
    private lateinit var mDescription: TextView
    private lateinit var mNumberOfRoom: TextView
    private lateinit var mAvailable: TextView
    private lateinit var idRealty: Integer
    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory(
            (activity?.application as RealStateManagerApplication).repository,
            (activity?.application as RealStateManagerApplication).repositoryShot
        )
    }
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if ("DATA_ACTION" == intent.action == true) {
                //Les données sont passées et peuvent être récupérées via :
                // intent.getSerializableExtra("DATA_EXTRA");
                // intent.getIntExtra("DATA_EXTRA", 2);
                //recuperate idRealty
                idRealty = intent.extras!!.get(KEY_ID_REALTY) as Integer
                idRealty = 1 as Integer
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // LocalBroadcastManager.getInstance(requireContext()).registerReceiver(broadcastReceiver, IntentFilter("DATA_ACTION"))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val myToolbar = view.findViewById<Toolbar>(R.id.myToolbar2)
        //Hide topAppBar
        (activity as AppCompatActivity).findViewById<Toolbar>(R.id.topAppBar).visibility =
            View.INVISIBLE
        //myToolbar.title="Detail Fragment"
        myToolbar?.setNavigationOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_detailFragment_to_mainFragment)
        }

        mRecyclerView = view.findViewById(R.id.realtyShootRecyclerView)
        mImageView = view.findViewById(R.id.staticMapRealty)
        mLocation = view.findViewById(R.id.location)
        mDescription = view.findViewById(R.id.description)
        mNumberOfRoom = view.findViewById(R.id.numberOfRoom)
        mSurface = view.findViewById(R.id.surface)
        mAvailable = view.findViewById(R.id.available)
        mImageadapter = GalleryAdapter()
        mRecyclerView.adapter = mImageadapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )


        val mArrayBitmap = ArrayList<Bitmap>()
        idRealty = (arguments?.get(KEY_ID_REALTY) as? Integer)!!
        //Fetch realty bitmap from datebase
        myViewModel.allShotByIdRealty(idRealty as Int).observe(viewLifecycleOwner) {
            var size = it.size
            size--
            for (i in 0..size) {
                mArrayBitmap.add(it[i].shot)
            }
            mImageadapter.submitList(mArrayBitmap)

        }
        myViewModel.getRealtyById(idRealty.toInt()).observe(viewLifecycleOwner) {
            updateUI(it)
            //val imageUri=getUriStaticMap(it.address)
            //print("imageUri= $imageUri")
            /*   Picasso.get()
                   .load(imageUri)
                   .placeholder(R.drawable.ic_launcher_foreground)
                   .into(mImageView)

             */
        }
        return view
    }

    private fun updateUI(it: Realty) {
        mLocation.text = it.address
        mDescription.text = it.description
        mNumberOfRoom.text = it.numberOfPiece.toString()
        mSurface.text = it.surface.toString() + getString(R.string.uniteSurface)
        if (it.isAvailable) {
            mAvailable.text = getString(R.string.availableText)
        } else {
            mAvailable.text = getString(R.string.SoldText)
        }
    }

    private fun getUriStaticMap(location: String): String {
        // val imageUri = "https://maps.googleapis.com/maps/api/staticmap?center=Rabat&zoom=12&size=400x400&markers=color:red&maptype=roadmap&key=AIzaSyBG86SUW6eSNtqTIcNZLfyCMdWjTKKoUQo"
        val keyMap = "AIzaSyBG86SUW6eSNtqTIcNZLfyCMdWjTKKoUQo"
        val mapUrlInitial = "https://maps.googleapis.com/maps/api/staticmap?center=$location"
        val mapUrlProperties = "&zoom=12&size=400x400&markers=color:red"
        val mapUrlMapType = "&maptype=roadmap&key=$keyMap"
        return mapUrlInitial + mapUrlProperties + mapUrlMapType
    }


}