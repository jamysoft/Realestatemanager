package com.openclassrooms.realestatemanager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    lateinit var  mImageadapter:CustomizedGalleryAdapter
    lateinit var  mGridview: GridView
    lateinit var  mImageView: ImageView
    lateinit var mSurface:TextView
    lateinit var  mLocation: TextView
    lateinit var  mDescription: TextView
    lateinit var mNumberOfRoom:TextView
    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory((application as RealStateManagerApplication).repository,(application as RealStateManagerApplication).repositoryShot)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        mGridview=findViewById(R.id.gridview)
        mImageView=findViewById(R.id.staticMapRealty)
        mLocation=findViewById(R.id.location)
        mDescription=findViewById(R.id.description)
        mNumberOfRoom=findViewById(R.id.numberOfRoom)
        mSurface=findViewById(R.id.surface)
        //   actionBar?.setDisplayHomeAsUpEnabled(true)
        //recuperer idRealty
        val idRealty= intent.extras!!.get(KEY_ID_REALTY)
        var mArrayBitmap=ArrayList<Bitmap>()
        //Fetch realty bitmap from datebase
        myViewModel.allShotByIdRealty(idRealty as Int).observe(this) {
            var size = it.size
            size--
            for (i in 0..size) {
                mArrayBitmap.add(it[i].shot)
            }
            mImageadapter = CustomizedGalleryAdapter(this@DetailActivity, mArrayBitmap)
            mGridview.adapter = mImageadapter
        }
        myViewModel.getRealtyById(idRealty.toInt()).observe(this){
            updateUI(it)
            var imageUri=getUriStaticMap(it.address)
            print("imageUri= $imageUri")
            Picasso.get()
                .load(imageUri)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(mImageView)
        }
    }

    private fun updateUI(it: Realty) {
        mLocation.text = it.address
        mDescription.text=it.description
        mNumberOfRoom.text=it.numberOfPiece.toString()
        mSurface.text=it.surface.toString()
    }

    private fun getUriStaticMap(location:String) :String{
        // val imageUri = "https://maps.googleapis.com/maps/api/staticmap?center=Rabat&zoom=12&size=400x400&markers=color:red&maptype=roadmap&key=AIzaSyBG86SUW6eSNtqTIcNZLfyCMdWjTKKoUQo"
        val keyMap="AIzaSyBG86SUW6eSNtqTIcNZLfyCMdWjTKKoUQo"
        val mapUrlInitial = "https://maps.googleapis.com/maps/api/staticmap?center=$location"
        val mapUrlProperties = "&zoom=12&size=400x400&markers=color:red"
        val mapUrlMapType = "&maptype=roadmap&key=$keyMap"
        return mapUrlInitial + mapUrlProperties  +mapUrlMapType
    }
}