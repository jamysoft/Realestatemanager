package com.openclassrooms.realestatemanager

import android.content.*
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.RangeSlider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.openclassrooms.realestatemanager.R.*
import com.openclassrooms.realestatemanager.utils.Utils.Companion.isInternetAvailable
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory


class MainFragment : Fragment() {

    private lateinit var myToolbar: MaterialToolbar
    private lateinit var messageEmptyRecyclerView: TextView
    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory(
            (activity?.application as RealStateManagerApplication).repository,
            (activity?.application as RealStateManagerApplication).repositoryShot
        )
    }
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: ListRealtyAdapter
    private var searchIsApplicated = false
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if ("DATA_ACTION" == intent.action == true) {
                //Les données sont passées et peuvent être récupérées via :
                // intent.getSerializableExtra("DATA_EXTRA");
                // intent.getIntExtra("DATA_EXTRA", 2);
                if (intent.getStringExtra(KEY_ADD_REALTY) == "OK") {
                    Toast.makeText(context, "Realty is added  successfully !", Toast.LENGTH_SHORT)
                        .show()
                }
                if (intent.getStringExtra(KEY_EDIT_REALTY) == "OK") {
                    Toast.makeText(
                        context,
                        "Realty is up dated  successfully !",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu (true)
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(broadcastReceiver, IntentFilter("DATA_ACTION"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(layout.fragment_main, container, false)
        val activity = activity as AppCompatActivity
        myToolbar = view.findViewById(R.id.topAppBar) as MaterialToolbar
        messageEmptyRecyclerView = view.findViewById(R.id.messageEmptyRecyclerView)
        activity.setSupportActionBar(myToolbar)
        if(isInternetAvailable(view.context) == true){
                myToolbar?.setNavigationOnClickListener {
                    NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_mapsFragment)
                }
            }
        else{
            myToolbar.navigationIcon=null
        }

        mRecyclerView = view.findViewById(R.id.recycleViewListRealty)
        adapter = ListRealtyAdapter(requireActivity().menuInflater, myViewModel, this)
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        //Fetch Realty from Database
        myViewModel.getAllRealtyItem.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                messageEmptyRecyclerView.text = "No realty in database !"
                messageEmptyRecyclerView.visibility = View.VISIBLE
            } else {
                adapter.submitList(it)
                messageEmptyRecyclerView.visibility = View.INVISIBLE
            }
            println("number of realty is ${it.size}")
        }
        setHasOptionsMenu(true)
        return view
    }

    //add menu in Fragement
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
        super.onCreateOptionsMenu(menu, inflater);
        //return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val intent = Intent(context, AddActivity::class.java)

        when (item.itemId) {
            R.id.add -> {
                Toast.makeText(context, "add selected", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }
            R.id.search -> {
                myToolbar.setBackgroundColor(Color.parseColor("#FF6200EE"))
                if (searchIsApplicated) {
                    myViewModel.getAllRealtyItem.observe(viewLifecycleOwner) {
                        if (it.isEmpty()) {
                            messageEmptyRecyclerView.text = "No realty in database !"
                            messageEmptyRecyclerView.visibility = View.VISIBLE
                        } else {
                            adapter.submitList(it)
                            messageEmptyRecyclerView.visibility = View.INVISIBLE
                        }
                        searchIsApplicated = false
                        // myToolbar.setBackgroundColor(Color.YELLOW)
                    }
                } else {
                    val inflater = layoutInflater
                    val alertLayout = inflater.inflate(layout.search_alert, null);
                    context?.let {
                        showAlertDialog(it, alertLayout)
                    }
                }

                // Toast.makeText(this,"Search selected",Toast.LENGTH_LONG).show()
            }
            R.id.estimate -> {
                val intent = Intent(context, SimulationActivity::class.java)
                Toast.makeText(context, "Estimate selected", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showAlertDialog(context: Context, alertLayout: View) {

        MaterialAlertDialogBuilder(context)
            .setTitle("Search")
            .setIcon(drawable.ic_search_24dp)
            // .setMessage("Choisir type de recherche ")
            .setNeutralButton("Quitter", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    println("setNeutralButton")
                    Toast.makeText(context, "setNeutralButton selected", Toast.LENGTH_LONG).show()
                }
            })
            .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    myToolbar.setBackgroundColor(Color.parseColor("#FF018786"))
                    var minSurface = alertLayout.findViewById<RangeSlider>(R.id.minSurface).values[0].toInt()
                    var maxSurface = alertLayout.findViewById<RangeSlider>(R.id.maxSurface).values[0].toInt()
                    var maxSeniority = alertLayout.findViewById<RangeSlider>(R.id.maxSeniority).values[0].toInt()
                    var isAvailable = 0
                    if (alertLayout.findViewById<SwitchMaterial>(R.id.availabilitySwitch).isChecked) {
                        isAvailable = 1
                    }
                    println("minSurface = $minSurface")
                    println("maxSurface = $maxSurface")
                    println("isAvailable = $isAvailable")
                    println("maxSeniority = $maxSeniority")
                    searchIsApplicated = true
                    myViewModel.getRealtyBySurfaceAndSiniority(
                        maxSeniority,
                        isAvailable,
                        minSurface,
                        maxSurface
                    ).observe(viewLifecycleOwner) {
                        if (it.isEmpty()) {
                            messageEmptyRecyclerView.text =
                                "No realty  correspond tou your search !"
                            messageEmptyRecyclerView.visibility = View.VISIBLE
                        }
                        adapter.submitList(it)
                        // myToolbar.setBackgroundColor(Color.YELLOW)
                    }

                    Toast.makeText(context, "setPositiveButton selected", Toast.LENGTH_LONG).show()
                }
            })
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


}