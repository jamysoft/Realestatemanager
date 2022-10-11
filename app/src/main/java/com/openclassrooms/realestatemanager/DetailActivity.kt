package com.openclassrooms.realestatemanager

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    lateinit var typeRealtyTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //recuperer idRealty
        val idRealty= intent.extras!!.get(KEY_ID_REALTY)
        typeRealtyTextView=findViewById(R.id.typeRealty)
        typeRealtyTextView.setText("$idRealty")
        actionBar?.setDisplayHomeAsUpEnabled(true)



    }
}