package com.openclassrooms.realestatemanager.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.net.wifi.WifiManager
import com.openclassrooms.realestatemanager.models.Shot
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class Utils {
    companion object {
        fun convertDollarToEuro(dollars: Int): Int {
            return Math.round(dollars * 0.812).toInt()
        }

        /**
         * Conversion de la date d'aujourd'hui en un format plus approprié
         * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
         * @return
         */
        fun getTodayDate(): String? {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            return dateFormat.format(Date())
        }

        /**
         * Vérification de la connexion réseau
         * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
         * @param context
         * @return
         */
        fun isInternetAvailable(context: Context): Boolean? {
            val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            return wifi.isWifiEnabled
        }

        fun integerToString(number: Integer): String {
            return number.toString()
        }


        fun convertListShotToArrayBitmap(listShot: List<Shot>): ArrayList<Bitmap> {
            var arrayBitmap = ArrayList<Bitmap>()
            var size = listShot.size
            size--
            for (i in 0..size) {
                arrayBitmap.add(listShot[i].shot)
            }
            return arrayBitmap
        }

        fun convertUriToBitmap(uri: Uri, contentResolver: ContentResolver): Bitmap {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val stream = ByteArrayOutputStream()
            // mByteArray.add(stream.toByteArray())
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return bitmap
        }
        fun arrondiNumber(number:Float):String{
            val df = DecimalFormat("0.##")
            return df.format(number)
        }
    }

}