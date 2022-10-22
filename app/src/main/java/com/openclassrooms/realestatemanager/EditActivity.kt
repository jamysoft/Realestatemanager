package com.openclassrooms.realestatemanager

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.models.Shot
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.Utils.Companion.listShotToArrayBitmap
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory
import java.io.ByteArrayOutputStream
const val KEY_EDIT_REALTY = "key_edit_realty"
class EditActivity : AppCompatActivity() {
    private lateinit var  mToolbar: Toolbar
    private  lateinit var  mTypeTextField: TextInputLayout
    private lateinit var  mPriceTextField: TextInputLayout
    private lateinit var  mSurfaceTextField: TextInputLayout
    private lateinit var  mNumberOfPieceTextField: TextInputLayout
    private lateinit var  mDescriptionTextField: TextInputLayout
    private lateinit var  mTownTextField: TextInputLayout
    private lateinit var  mAddressTextField: TextInputLayout
    private lateinit var  mButton: Button
    private lateinit var mFloatingActionButton:FloatingActionButton
    private var mArrayBitmap: MutableList<Bitmap> = ArrayList()
    private lateinit var  imageadapter:GalleryAdapter
    private lateinit var  mRecyclerView: RecyclerView

    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory((application as RealStateManagerApplication).repository,(application as RealStateManagerApplication).repositoryShot)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        mToolbar=findViewById(R.id.myToolbarEdit)
        mTypeTextField=findViewById(R.id.typeTextFieldEdit)
        mPriceTextField=findViewById(R.id.priceTextFieldEdit)
        mSurfaceTextField=findViewById(R.id.surfaceTextFieldEdit)
        mNumberOfPieceTextField=findViewById(R.id.numberOfPieceTextFieldEdit)
        mDescriptionTextField=findViewById(R.id.descriptionTextFieldEdit)
        mTownTextField=findViewById(R.id.townTextFieldEdit)
        mAddressTextField=findViewById(R.id.adressTextFieldEdit)
        mButton=findViewById(R.id.editButton)
        mFloatingActionButton=findViewById(R.id.editShot)
        mRecyclerView=findViewById(R.id.realtyShootRecyclerViewEdit)

        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var  thereIsError=false
        //recuperate idRealty From intent
        val idRealty= intent.extras!!.get(KEY_EDIT_REALTY)
        imageadapter=GalleryAdapter()
        mRecyclerView.adapter=imageadapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.layoutManager =layoutManager
        mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        myViewModel.allShotByIdRealty(idRealty as Int).observe(this){
            mArrayBitmap=listShotToArrayBitmap(it)
            imageadapter.submitList(mArrayBitmap)
        }

        myViewModel.getRealtyById(idRealty).observe(this){
           // printCurrentRealtyIntoUI(it)
            mTypeTextField.editText?.setText(it.type)
            mPriceTextField.editText?.setText(it.price.toString())
            mSurfaceTextField.editText?.setText(it.surface.toString())
            mNumberOfPieceTextField.editText?.setText(it.numberOfPiece.toString())
            mDescriptionTextField.editText?.setText(it.description)
            mAddressTextField.editText?.setText(it.address)
            mTownTextField.editText?.setText(it.town)
        }

/*
        //addTextChangedListener for mTypeTextField
        mTypeTextField.editText?.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! >4) {
                    mTypeTextField.error = ""
                    thereIsError=false
                }
                else{
                    mTypeTextField.error="Enter a validate Type"
                    thereIsError=true
                }
            }
        })
        //addTextChangedListener for mPriceTextField
        mPriceTextField.editText?.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! >3) {
                    mPriceTextField.error = ""
                    thereIsError=false
                }
                else{
                    mPriceTextField.error="Enter a validate price"
                    thereIsError=true
                }
            }
        })
        //addTextChangedListener for mSurfaceTextField
        mSurfaceTextField.editText?.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! >1) {
                    mSurfaceTextField.error = ""
                    thereIsError=false
                }
                else{
                    mSurfaceTextField.error="Enter validate a surface"
                    thereIsError=true
                    println("Enter validate a information + in else set mSurfaceTextField")
                }
            }
        })*/
        //Action mButton
        mButton.setOnClickListener{
            println("edit is cliked")
            Toast.makeText(this, "EDIT button is clicked ", Toast.LENGTH_SHORT).show()
            thereIsError=false
            // Get. input text
            val typeText: Editable? = mTypeTextField.editText?.text
            val priceText:Editable? = mPriceTextField.editText?.text
            val surfaceText:Editable ?= mSurfaceTextField.editText?.text
            val numberOfPieceText:Editable? = mNumberOfPieceTextField.editText?.text
            val descriptionText:Editable? = mDescriptionTextField.editText?.text
            val adressText:Editable? = mAddressTextField.editText?.text
            val townText: Editable? = mTownTextField.editText?.text
            if(typeText.toString().isEmpty()|| priceText.toString().isEmpty() || surfaceText.toString().isEmpty() ||
                numberOfPieceText.toString().isEmpty() || descriptionText.toString().isEmpty()|| adressText.toString().isEmpty()||
                townText.toString().isEmpty()) {
                println("Enter validate a information + in if in set clicked")
                thereIsError=true
            }
            if(thereIsError){
                Toast.makeText(this, "Enter validate a information", Toast.LENGTH_SHORT).show()
                println("Enter validate a information + in buton set clicked")
            }
            else {
                if (mArrayBitmap.size == 0) {
                    Toast.makeText(this, "Choose at least one picture !", Toast.LENGTH_SHORT).show()
                    println("Choose at least one picture")
                }
                else{
                    println("we are going to update realty")
                    myViewModel.getRealtyById(idRealty).observe(this){
                        // printCurrentRealtyIntoUI(it)
                        val price = priceText.toString().toInt()
                        val surface = surfaceText.toString().toInt()
                        val numberOfPiece = numberOfPieceText.toString().toInt()
                        val description=descriptionText.toString()
                        val type=typeText.toString()
                        val town=townText.toString()
                        val address=adressText.toString()
                        it.type= type
                        it.price=price
                        it.surface=surface
                        it.numberOfPiece=numberOfPiece
                        it.description=description
                        it.town=town
                        it.address=address
                        myViewModel.updateRealty(it)
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra(KEY_EDIT_REALTY, "OK")
                        startActivity(intent)
                    }
                }
            }
        }

        mFloatingActionButton.setOnClickListener{
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
            mArrayBitmap.clear()
            println("After clear "+mArrayBitmap.size)
            imageadapter.submitList(mArrayBitmap)
            if (data.clipData != null) {
                var itemCount = data.clipData!!.itemCount
                println("you have picked $itemCount Images")
                Toast.makeText(this, "You have picked  $itemCount Images", Toast.LENGTH_LONG).show()
                itemCount--
                for (i in 0..itemCount) {
                    // adding imageuri in array
                    val imageuri = data.clipData?.getItemAt(i)?.uri
                    imageuri?.let {
                        val inputStream = contentResolver.openInputStream(imageuri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        val stream = ByteArrayOutputStream()
                        //   mByteArray.add(stream.toByteArray())
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        //  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, mByteArray.si)
                        mArrayBitmap.add(bitmap)
                    }
                }
                // setting 1st selected image into image switcher
                //  img.setImageURI(data.clipData?.getItemAt(0)?.uri);

            }  // show this if one image is selected
            else {
                val uri = data.data
                uri?.let {
                    val inputStream = contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    val stream = ByteArrayOutputStream()
                    // mByteArray.add(stream.toByteArray())
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    mArrayBitmap.add(bitmap)
                }
                Toast.makeText(this, "You picked  one Image ", Toast.LENGTH_LONG).show()
                //mImageView.setImageURI(data.data)
            }
            //adapter.submitList(mArrayUri)
            println("mArrayUri ${mArrayBitmap.size} ")
            mRecyclerView.background=null
            imageadapter.submitList(mArrayBitmap)
            //importante pour mette à jour les images du recyclerview !!! sinon les recyclerview continet les ancian image
            imageadapter.notifyDataSetChanged()


        } else {
            // show this if no image is selected
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }
}