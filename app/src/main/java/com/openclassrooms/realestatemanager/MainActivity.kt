package com.openclassrooms.realestatemanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.openclassrooms.realestatemanager.viewModels.*


class MainActivity : AppCompatActivity() {
    private lateinit var myToolbar:MaterialToolbar
    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory((application as RealStateManagerApplication).repository,(application as RealStateManagerApplication).repositoryShot)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myToolbar=findViewById(R.id.topAppBar)
      setSupportActionBar(myToolbar)

        val mRecyclerView = findViewById<RecyclerView>(R.id.recycleViewListRealty)
        val adapter = ListRealtyAdapter(menuInflater,myViewModel)
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        if(intent.getStringExtra(KEY_ADD_REALTY)=="OK"){
            Toast.makeText(this, "Realty is added  successfully !", Toast.LENGTH_SHORT).show()
            }
        if(intent.getStringExtra(KEY_EDIT_REALTY)=="OK"){
            Toast.makeText(this, "Realty is up dated  successfully !", Toast.LENGTH_SHORT).show()
        }
        //Fetch Realty from Database
         myViewModel.getAllRealtyItem.observe(this@MainActivity) {
             adapter.submitList(it)
         }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this,AddActivity::class.java)
        when (item.itemId) {
            R.id.add -> startActivity(intent)
            R.id.search -> {
                Toast.makeText(this,"Search selected",Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}