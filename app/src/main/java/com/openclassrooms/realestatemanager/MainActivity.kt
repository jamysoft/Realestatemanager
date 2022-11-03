package com.openclassrooms.realestatemanager

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var myToolbar: MaterialToolbar
    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory(
            (application as RealStateManagerApplication).repository,
            (application as RealStateManagerApplication).repositoryShot
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(myToolbar)


        /* val mRecyclerView = findViewById<RecyclerView>(R.id.recycleViewListRealty)
         mRecyclerView.visibility= View.INVISIBLE
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

         */

    }
    /*

   override fun onCreateOptionsMenu(menu: Menu): Boolean {
       menuInflater.inflate(R.menu.top_app_bar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this,AddActivity::class.java)
        when (item.itemId) {
            R.id.add -> startActivity(intent)
            R.id.search -> {
                val inflater = layoutInflater
                val alertLayout = inflater.inflate(R.layout.search_alert, null);
                showAlertDialog(this,alertLayout)
               // Toast.makeText(this,"Search selected",Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showAlertDialog(context: Context,alertLayout:View){

        MaterialAlertDialogBuilder(context)
            .setTitle("Search")
            .setIcon(R.drawable.ic_search_24dp)
           // .setMessage("Choisir type de recherche ")
            .setNeutralButton("Quitter",object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    println("setNeutralButton")
                    Toast.makeText(context,"setNeutralButton selected",Toast.LENGTH_LONG).show()
                }})
            .setPositiveButton("OK",object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    println("setPositiveButton")
                    Toast.makeText(context,"setPositiveButton selected",Toast.LENGTH_LONG).show()
                }})
             .setView(alertLayout)
            .show()

        /*
         View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final TextInputEditText etUsername = alertLayout.findViewById(R.id.tiet_username);
        final TextInputEditText etPassword = alertLayout.findViewById(R.id.tiet_password);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Login");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
         */

    }


     */


}