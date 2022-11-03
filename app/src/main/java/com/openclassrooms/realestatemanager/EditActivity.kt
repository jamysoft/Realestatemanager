package com.openclassrooms.realestatemanager

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.models.Shot
import com.openclassrooms.realestatemanager.utils.Utils.Companion.convertListShotToArrayBitmap
import com.openclassrooms.realestatemanager.utils.Utils.Companion.convertUriToBitmap
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory

const val KEY_EDIT_REALTY = "key_edit_realty"

class EditActivity : AppCompatActivity() {
    private lateinit var mToolbar: Toolbar
    private lateinit var mTypeTextField: TextInputLayout
    private lateinit var mPriceTextField: TextInputLayout
    private lateinit var mSurfaceTextField: TextInputLayout
    private lateinit var mNumberOfPieceTextField: TextInputLayout
    private lateinit var mDescriptionTextField: TextInputLayout
    private lateinit var mTownTextField: TextInputLayout
    private lateinit var mAddressTextField: TextInputLayout
    private lateinit var mButton: Button
    private lateinit var mFloatingActionButton: FloatingActionButton
    private var mArrayBitmapFromDatabase: MutableList<Bitmap> = ArrayList()
    private var mArrayBitmapAtEnd: MutableList<Bitmap> = ArrayList()
    private lateinit var imageadapter: GalleryAdapter
    private lateinit var mRecyclerView: RecyclerView
    lateinit var idRealty: Integer


    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory(
            (application as RealStateManagerApplication).repository,
            (application as RealStateManagerApplication).repositoryShot
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        mToolbar = findViewById(R.id.myToolbarEdit)
        mTypeTextField = findViewById(R.id.typeTextFieldEdit)
        mPriceTextField = findViewById(R.id.priceTextFieldEdit)
        mSurfaceTextField = findViewById(R.id.surfaceTextFieldEdit)
        mNumberOfPieceTextField = findViewById(R.id.numberOfPieceTextFieldEdit)
        mDescriptionTextField = findViewById(R.id.descriptionTextFieldEdit)
        mTownTextField = findViewById(R.id.townTextFieldEdit)
        mAddressTextField = findViewById(R.id.adressTextFieldEdit)
        mButton = findViewById(R.id.editButton)
        mFloatingActionButton = findViewById(R.id.editShot)
        mRecyclerView = findViewById(R.id.realtyShootRecyclerViewEdit)

        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var thereIsError = false
        //recuperate idRealty From intent
        idRealty = intent.extras!!.get(KEY_EDIT_REALTY) as Integer
        imageadapter = GalleryAdapter()
        mRecyclerView.adapter = imageadapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        myViewModel.allShotByIdRealty(idRealty as Int).observe(this) {
            mArrayBitmapFromDatabase = convertListShotToArrayBitmap(it)
            if (mArrayBitmapAtEnd.size == 0) {
                imageadapter.submitList(mArrayBitmapFromDatabase)
            }
        }

        myViewModel.getRealtyById(idRealty as Int).observe(this) {
            // printCurrentRealtyIntoUI(it)
            mTypeTextField.editText?.setText(it.type)
            mPriceTextField.editText?.setText(it.price.toString())
            mSurfaceTextField.editText?.setText(it.surface.toString())
            mNumberOfPieceTextField.editText?.setText(it.numberOfPiece.toString())
            mDescriptionTextField.editText?.setText(it.description)
            mAddressTextField.editText?.setText(it.address)
            mTownTextField.editText?.setText(it.town)
        }

        //Action mButton
        mButton.setOnClickListener {
            println("edit is cliked")
            Toast.makeText(this, "EDIT button is clicked ", Toast.LENGTH_SHORT).show()
            thereIsError = false
            // Get. input text
            val typeText: Editable? = mTypeTextField.editText?.text
            val priceText: Editable? = mPriceTextField.editText?.text
            val surfaceText: Editable? = mSurfaceTextField.editText?.text
            val numberOfPieceText: Editable? = mNumberOfPieceTextField.editText?.text
            val descriptionText: Editable? = mDescriptionTextField.editText?.text
            val adressText: Editable? = mAddressTextField.editText?.text
            val townText: Editable? = mTownTextField.editText?.text
            if (typeText.toString().isEmpty() || priceText.toString()
                    .isEmpty() || surfaceText.toString().isEmpty() ||
                numberOfPieceText.toString().isEmpty() || descriptionText.toString()
                    .isEmpty() || adressText.toString().isEmpty() ||
                townText.toString().isEmpty()
            ) {
                println("Enter validate a information + in if in set clicked")
                thereIsError = true
            }
            if (thereIsError) {
                Toast.makeText(this, "Enter validate a information", Toast.LENGTH_SHORT).show()
                println("Enter validate a information + in buton set clicked")
            } else {
                ///DEBUT update shots
                var size = mArrayBitmapAtEnd.size
                println(" nombre shot " + size)

                if (size == 0) {
                    println("we are going to update realty sans shot" + size)
                } else {
                    println("we are going to update realty and shot " + size)
                    myViewModel.deleteAllShotOfRealty(idRealty.toInt())
                    size--
                    for (i in 0..size) {
                        var description = "GeneralShot"
                        if (i == 0) {
                            description = "MainShot"
                        }
                        val shot = Shot(null, description, mArrayBitmapAtEnd[i], idRealty.toInt())
                        myViewModel.insert(shot)
                    }
                }
                //

                myViewModel.getRealtyById(idRealty as Int).observe(this) {
                    // printCurrentRealtyIntoUI(it)
                    val price = priceText.toString().toInt()
                    val surface = surfaceText.toString().toInt()
                    val numberOfPiece = numberOfPieceText.toString().toInt()
                    val description = descriptionText.toString()
                    val type = typeText.toString()
                    val town = townText.toString()
                    val address = adressText.toString()
                    it.type = type
                    it.price = price
                    it.surface = surface
                    it.numberOfPiece = numberOfPiece
                    it.description = description
                    it.town = town
                    it.address = address
                    myViewModel.updateRealty(it)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(KEY_EDIT_REALTY, "OK")
                    startActivity(intent)
                }
            }
        }

        mFloatingActionButton.setOnClickListener {
            // mArrayBitmap.clear()
            val intentImg = Intent(Intent.ACTION_GET_CONTENT)
            // setting type to select to be image
            intentImg.type = "image/*"
            // allowing multiple image to be selected
            intentImg.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intentImg, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            mArrayBitmapAtEnd.clear()
            //  println("After clear "+mArrayBitmap.size)
            imageadapter.submitList(mArrayBitmapAtEnd)
            if (data.clipData != null) {
                var itemCount = data.clipData!!.itemCount
                println("you have picked $itemCount Images")
                Toast.makeText(this, "You have picked  $itemCount Images", Toast.LENGTH_LONG).show()
                itemCount--
                for (i in 0..itemCount) {
                    // adding imageuri in array
                    val imageUri = data.clipData?.getItemAt(i)?.uri
                    imageUri?.let {
                        val bitmap = convertUriToBitmap(it, contentResolver)
                        mArrayBitmapAtEnd.add(bitmap)
                    }
                }
                // setting 1st selected image into image switcher
                //  img.setImageURI(data.clipData?.getItemAt(0)?.uri);

            }  // show this if one image is selected
            else {
                val uri = data.data
                uri?.let {
                    val bitmap = convertUriToBitmap(it, contentResolver)
                    mArrayBitmapAtEnd.add(bitmap)
                }
                Toast.makeText(this, "You picked  one Image ", Toast.LENGTH_LONG).show()
                //mImageView.setImageURI(data.data)
            }
            //adapter.submitList(mArrayUri)
            println("mArrayUri end ${mArrayBitmapAtEnd.size} ")
            mRecyclerView.background = null
            imageadapter.submitList(mArrayBitmapAtEnd)
            //importante pour mette à jour les images du recyclerview !!! sinon les recyclerview continet les ancian image
            imageadapter.notifyDataSetChanged()

        } else {
            // show this if no image is selected
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }
}