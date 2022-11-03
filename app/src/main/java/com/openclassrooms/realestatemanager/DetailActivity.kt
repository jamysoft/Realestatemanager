package com.openclassrooms.realestatemanager

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var mImageadapter: GalleryAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mImageView: ImageView
    private lateinit var mSurface: TextView
    private lateinit var mLocation: TextView
    private lateinit var mDescription: TextView
    private lateinit var mNumberOfRoom: TextView
    private lateinit var mAvailable: TextView
    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory(
            (application as RealStateManagerApplication).repository,
            (application as RealStateManagerApplication).repositoryShot
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mRecyclerView = findViewById(R.id.realtyShootRecyclerView)
        mImageView = findViewById(R.id.staticMapRealty)
        mLocation = findViewById(R.id.location)
        mDescription = findViewById(R.id.description)
        mNumberOfRoom = findViewById(R.id.numberOfRoom)
        mSurface = findViewById(R.id.surface)
        mAvailable = findViewById(R.id.available)
        mImageadapter = GalleryAdapter()
        mRecyclerView.adapter = mImageadapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        //recuperate idRealty
        val idRealty = intent.extras!!.get(KEY_ID_REALTY)
        val mArrayBitmap = ArrayList<Bitmap>()
        //Fetch realty bitmap from datebase
        myViewModel.allShotByIdRealty(idRealty as Int).observe(this) {
            var size = it.size
            size--
            for (i in 0..size) {
                mArrayBitmap.add(it[i].shot)
            }
            mImageadapter.submitList(mArrayBitmap)

        }
        myViewModel.getRealtyById(idRealty.toInt()).observe(this) {
            updateUI(it)
            val imageUri = getUriStaticMap(it.address)
            print("imageUri= $imageUri")
            Picasso.get()
                .load(imageUri)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(mImageView)
        }
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