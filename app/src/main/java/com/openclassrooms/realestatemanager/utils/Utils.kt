package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.wifi.WifiManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.Shot
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class Utils{
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
            val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
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
            return ""
        }


    fun listShotToArrayBitmap(listShot:List<Shot>): ArrayList<Bitmap> {
        var arrayBitmap=ArrayList<Bitmap>()
        var size=listShot.size
        size--
        for(i in 0..size){
            arrayBitmap.add(listShot.get(i).shot)
        }
        return arrayBitmap
    }


    }

}