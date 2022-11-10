package com.openclassrooms.realestatemanager.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.openclassrooms.realestatemanager.models.Realty
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

        fun convertEuroToDolar(euro: Int): Int {
            return Math.round(euro * 1.231).toInt()
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

        fun getTodayDateFr(): String? {
            val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
            return dateFormat.format(Date())
        }

        /**
         * Vérification de la connexion réseau
         * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
         * @param context
         * @return
         */

        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("MissingPermission")
        fun isInternetAvailable(context: Context): Boolean? {
          //  val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
          //  return wifi.isWifiEnabled

           var connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

                if (connectivityManager != null) {
                    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    if (capabilities != null) {
                        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                            return true
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                            return true
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                            return true
                        }
                    }
                }
                return false
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