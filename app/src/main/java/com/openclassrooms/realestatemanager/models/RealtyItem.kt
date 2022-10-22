package com.openclassrooms.realestatemanager.models

import android.graphics.Bitmap

class RealtyItem (
                   val idRealty: Int,
                  val type:String,
                  val price:Int,
                  val surface:Int,
                 val numberOfPiece:Int,
                 val description:String,
                  val town:String,
                  val address:String,
                val isAvailable:Boolean,
                 val entryDate: String,
                 val saleDate: String?,
                   val shot:Bitmap?

                )
