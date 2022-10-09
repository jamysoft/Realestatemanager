package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.openclassrooms.realestatemanager.viewModels.*


class MainActivity : AppCompatActivity() {
    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory((application as RealStateManagerApplication).repository)
    }
    private val myViewModel2: AgentViewModel by viewModels {
        AgentViewModelFactory((application as RealStateManagerApplication).repository2)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  myToolbar=findViewById(R.id.myToolbar)
      //  setSupportActionBar(myToolbar)
        myViewModel.allRealty.observe(this){
            println("size = ${it.size}+list :"+it.toString())

        }
        myViewModel2.allAgent.observe(this){
            println("size = ${it.size}+list :"+it.toString())
        }

    }
}