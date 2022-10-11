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

lateinit var myToolbar:MaterialToolbar
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
        myToolbar=findViewById(R.id.topAppBar)
      setSupportActionBar(myToolbar)

        val mRecyclerView = findViewById<RecyclerView>(R.id.recycleViewListRealty)
        val adapter = ListRealtyAdapter()
        mRecyclerView.adapter = adapter
       mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    /*    mRecyclerView?.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false)

     */
        myViewModel.allRealty.observe(this){
            println("size = ${it.size}+list :"+it.toString())
            it.let {
                adapter.submitList(it)
            }

        }
        myViewModel2.allAgent.observe(this){
            println("size = ${it.size}+list :"+it.toString())
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
            R.id.search -> Toast.makeText(this,"Search selected",Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}