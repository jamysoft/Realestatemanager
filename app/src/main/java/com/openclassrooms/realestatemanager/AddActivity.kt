package com.openclassrooms.realestatemanager

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.models.Shot
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.viewModels.RealStateManagerApplication
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModel
import com.openclassrooms.realestatemanager.viewModels.RealtyViewModelFactory
import java.io.ByteArrayOutputStream


const val KEY_ADD_REALTY = "key_add_realty"
class AddActivity : AppCompatActivity() {

    lateinit var  mToolbar: Toolbar
    lateinit var  mTypeTextField: TextInputLayout
    lateinit var  mPriceTextField: TextInputLayout
    lateinit var  mSurfaceTextField: TextInputLayout
    lateinit var  mNumberOfPieceTextField: TextInputLayout
    lateinit var  mDescriptionTextField: TextInputLayout
    lateinit var  mTownTextField: TextInputLayout
    lateinit var  mAdressTextField: TextInputLayout
    lateinit var  mButton: Button
    lateinit var mFloatingActionButton:FloatingActionButton
    lateinit var mImageView:ImageView
    var mArrayBitmap: MutableList<Bitmap> = ArrayList()
   // var mByteArray:MutableList<ByteArray> = ArrayList()
    lateinit var  imageadapter:CustomizedGalleryAdapter
    lateinit var mlinearLayout:LinearLayout
    lateinit var  gridview: GridView

    private val myViewModel: RealtyViewModel by viewModels {
        RealtyViewModelFactory((application as RealStateManagerApplication).repository,(application as RealStateManagerApplication).repositoryShot)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        mToolbar=findViewById(R.id.myToolbar)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mTypeTextField=findViewById(R.id.typeTextField)
        mPriceTextField=findViewById(R.id.priceTextField)
        mSurfaceTextField=findViewById(R.id.surfaceTextField)
        mNumberOfPieceTextField=findViewById(R.id.numberOfPieceTextField)
        mDescriptionTextField=findViewById(R.id.descriptionTextField)
        mTownTextField=findViewById(R.id.townTextField)
        mAdressTextField=findViewById(R.id.adressTextField)
        mButton=findViewById(R.id.addButton)
        mFloatingActionButton=findViewById(R.id.addShot)
        mImageView=findViewById(R.id.realtyShoot)
        mlinearLayout=findViewById(R.id.linearLayout)
      // grid layout
        gridview=findViewById(R.id.gridview)
        val shotByDefault = BitmapFactory.decodeResource(resources, R.drawable.belleappart)
        val shotByDefault2 = BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_add_photo_alternate_24)
        var mArrayBitmapDefault=ArrayList<Bitmap>()
        mArrayBitmapDefault.add(shotByDefault)
        mArrayBitmapDefault.add(shotByDefault2)
        imageadapter=CustomizedGalleryAdapter(this@AddActivity,mArrayBitmapDefault)
        gridview.adapter=imageadapter

       var  thereIsError =false

        // Get. input text
        var typeText: Editable? = mTypeTextField.editText?.text
        var priceText:Editable? = mPriceTextField.editText?.text
        var surfaceText:Editable ?= mSurfaceTextField.editText?.text
        var numberOfPieceText:Editable? = mNumberOfPieceTextField.editText?.text
        var descriptionText:Editable? = mDescriptionTextField.editText?.text
        var adressText:Editable? = mAdressTextField.editText?.text
        var townText: Editable? = mTownTextField.editText?.text

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
                }
            }
        })
        //Action mButton
        mButton.setOnClickListener{
            thereIsError=false
            if(typeText.toString().isEmpty()|| priceText.toString().isEmpty() || surfaceText.toString().isEmpty() ||
                numberOfPieceText.toString().isEmpty() || descriptionText.toString().isEmpty()|| adressText.toString().isEmpty()||
                townText.toString().isEmpty()) {
                thereIsError=true
            }
          //  Toast.makeText(this, "EROR $thereIsError"+ priceText.toString().isEmpty() +surfaceText.toString().isEmpty(), Toast.LENGTH_SHORT).show()
            if(thereIsError){
                Toast.makeText(this, "Enter validate a information", Toast.LENGTH_SHORT).show()
            }
               else {
                   var price=priceText.toString().toInt()
                   var surface=surfaceText.toString().toInt()
                   var numberOfPiece= numberOfPieceText.toString().toInt()
                   var TodayDate=Utils.getTodayDate()
                   var realty = Realty(null, typeText.toString(), price, surface, numberOfPiece, descriptionText.toString(), townText.toString(), adressText.toString(),
                    true, TodayDate!!, null, 1)
                   myViewModel.insert(realty).observe(this) {
                       //add  shots of the new   realty
                       var size=mArrayBitmap.size
                       size--
                        for(i in 0..size) {
                            var description="GeneralShot"
                            if(i==0){
                                description="MainShot"
                            }
                            val shot = Shot(null, description , mArrayBitmap[i], it.toInt())
                            myViewModel.insert(shot)
                        }
                }
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(KEY_ADD_REALTY, "OK")
                startActivity(intent)
            }
        }

        mFloatingActionButton.setOnClickListener{
            val intentImg = Intent(Intent.ACTION_GET_CONTENT)
            // setting type to select to be image
            intentImg.type = "image/*"
            // allowing multiple image to be selected
            intentImg.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(intentImg, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            mArrayBitmap.clear()
            if (data?.clipData != null) {
                var itemCount = data.clipData!!.itemCount
                mlinearLayout.layoutParams.width=itemCount*600
                println("you have picked $itemCount Images")
                Toast.makeText(this, "You have picked  $itemCount Images", Toast.LENGTH_LONG)
                    .show();
                itemCount--
                for (i in 0..itemCount) {
                    // adding imageuri in array
                    var imageuri = data.clipData?.getItemAt(i)?.uri
                    imageuri?.let {
                        val inputStream = contentResolver.openInputStream(imageuri)
                        var bitmap = BitmapFactory.decodeStream(inputStream)
                        val stream = ByteArrayOutputStream()
                     //   mByteArray.add(stream.toByteArray())
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                      //  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, mByteArray.si)
                        mArrayBitmap?.add(bitmap)
                    }
                }
                // setting 1st selected image into image switcher
                //  img.setImageURI(data.clipData?.getItemAt(0)?.uri);

            }  // show this if one image is selected
            else {
                var uri = data.data
                uri?.let {
                    val inputStream = contentResolver.openInputStream(uri)
                    var bitmap = BitmapFactory.decodeStream(inputStream)
                    val stream = ByteArrayOutputStream()
                   // mByteArray.add(stream.toByteArray())
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    mArrayBitmap?.add(bitmap)
                }
                Toast.makeText(this, "You picked  one Image ", Toast.LENGTH_LONG).show()
                mImageView.setImageURI(data.data);
            }
            //adapter.submitList(mArrayUri)
            println("mArrayUri ${mArrayBitmap?.size} ")
            var  imageadapter=CustomizedGalleryAdapter(this@AddActivity,mArrayBitmap)
            gridview.adapter=imageadapter

        } else {
            // show this if no image is selected
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

}