package com.openclassrooms.realestatemanager.myRoomDatabase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
       val byteArray= compresseImage(outputStream.toByteArray())
        return byteArray
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
       return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
    fun compresseImage(imageToCompress:ByteArray):ByteArray{
        var compressImage=imageToCompress
        while(compressImage.size>500000){
            val bitmap=BitmapFactory.decodeByteArray(compressImage,0,compressImage.size)
            val resized=Bitmap.createScaledBitmap(bitmap,
                (bitmap.width*0.80).toInt(),
                (bitmap.height*0.80).toInt(),true)
            val stream=ByteArrayOutputStream()
            resized.compress(Bitmap.CompressFormat.PNG,100,stream)
            compressImage=stream.toByteArray()
        }
        return compressImage
    }

}