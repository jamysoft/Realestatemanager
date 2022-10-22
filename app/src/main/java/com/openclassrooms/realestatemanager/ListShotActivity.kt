package com.openclassrooms.realestatemanager

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory

class ListShotActivity : AppCompatActivity() {
    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory((application as RealStateManagerApplication).repository,(application as RealStateManagerApplication).repositoryShot)
    }
    lateinit var image:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_shot)
        image=findViewById(R.id.shot)
        myViewModel.allShoot.observe(this, Observer {
           image.setImageBitmap(it[0].shot)
        })

    }
}