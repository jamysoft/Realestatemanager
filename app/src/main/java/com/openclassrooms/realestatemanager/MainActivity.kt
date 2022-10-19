package com.openclassrooms.realestatemanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.viewModels.*
import kotlinx.coroutines.launch
import java.util.*

lateinit var myToolbar:MaterialToolbar
class MainActivity : AppCompatActivity() {

    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory((application as RealStateManagerApplication).repository,(application as RealStateManagerApplication).repositoryShot)
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
        val adapter = ListRealtyAdapter(menuInflater,myViewModel)
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    /*  mRecyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)  */
        if(intent.getStringExtra(KEY_ADD_REALTY)=="OK"){
            Toast.makeText(this, "Realty is added  successfully", Toast.LENGTH_SHORT).show()
            }
        myViewModel.allRealty.observe(this){
            println("size = ${it.size}+list :"+it.toString())
            it.let {
                adapter.submitList(it)
            }

        }

        myViewModel2.allAgent.observe(this){
            println("size = ${it.size}+list :"+it.toString())
        }
        fun showTextualActionBar(context: Context) {
            val callback = object : ActionMode.Callback {
                override fun onCreateActionMode(mode: androidx.appcompat.view.ActionMode?, menu: Menu?): Boolean {
                    menuInflater.inflate(R.menu.top_app_bar, menu)
                    return true
                }

                override fun onPrepareActionMode(mode: androidx.appcompat.view.ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: androidx.appcompat.view.ActionMode?, item: MenuItem?): Boolean {
                    return when (item?.itemId) {
                        R.id.add -> {
                            println("option1")
                            Toast.makeText(context, "option1 click detected", Toast.LENGTH_SHORT).show()
                            // Handle share icon press
                            true

                        }
                        R.id.search -> {
                            println("option2")
                            Toast.makeText(context, "option2 click detected", Toast.LENGTH_SHORT).show()
                            // Handle delete icon press
                            true
                        }
                        else -> false
                    }
                }

                override fun onDestroyActionMode(mode: androidx.appcompat.view.ActionMode?) {

                }
            }
            val actionMode = startSupportActionMode(callback)
            actionMode?.title =  " is selected"
            Toast.makeText(this, "Long click detected", Toast.LENGTH_SHORT).show()
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
                lifecycleScope.launch {
                    myViewModel.updateStatusRealty()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

}