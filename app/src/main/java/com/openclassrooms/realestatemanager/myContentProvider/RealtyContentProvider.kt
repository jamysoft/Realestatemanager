package com.openclassrooms.realestatemanager.myContentProvider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.myRoomDatabase.RealstateRoomDatabase
import com.openclassrooms.realestatemanager.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class RealtyContentProvider :ContentProvider() {
    companion object {
        const val AUTHORITY = "com.openclassrooms.realestatemanager.myContentProvider"
         val TABLE_NAME = Realty::class.java.simpleName
         val URL = "content://$AUTHORITY/$TABLE_NAME"
        val URI_REALTY = Uri.parse(URL)
        val applicationScope = CoroutineScope(SupervisorJob())

    }
    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?): Cursor? {

        if (context != null) {
            val cursor: Cursor? = RealstateRoomDatabase.getDatabase(context!!,applicationScope).realtyDao().getRealtyItemWithCursor()
            cursor?.setNotificationUri(context!!.contentResolver, uri)
            return cursor
        }
        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun getType(p0: Uri): String? {
        return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {

        if (context != null && contentValues != null) {
            val  id =  RealstateRoomDatabase.getDatabase(context!!,applicationScope).realtyDao().insert(fromContentValues(contentValues))
            if (id != 0L  ) {
                context?.contentResolver?.notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }

        }

        throw  IllegalArgumentException("Failed to insert row into " + uri);
    }

    override fun delete(uri: Uri, p1: String?, p2: Array<out String>?): Int {
        if (context != null) {
            val count: Int =RealstateRoomDatabase.getDatabase(context!!,applicationScope).realtyDao().deleteRealtyById(ContentUris.parseId(uri) as Int)
            context!!.contentResolver.notifyChange(uri, null)
            return count
        }
        throw java.lang.IllegalArgumentException("Failed to delete row into $uri")
    }

    override fun update(uri: Uri, contentValues: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        if (context != null && contentValues!=null) {
            val count: Int =RealstateRoomDatabase.getDatabase(context!!,applicationScope).realtyDao().updateRealty(fromContentValues(contentValues!!))
            context!!.contentResolver.notifyChange(uri, null)
            return count
        }
        throw java.lang.IllegalArgumentException("Failed to update row into $uri")
    }
    //La classe ContentValues  nous permet de récupérer une valeur à partir d'une clé
    // Nous placerons ensuite cette valeur dans la propriété correspondante de l'objet Realty.
    fun fromContentValues(values: ContentValues): Realty {
        var realty=Realty(null, "", 0, 0, 0, "",
            "", " ",true, "", null, 1
        )
        if (values.containsKey("adress")) realty?.address=values.getAsString("adress")
        if (values.containsKey("town")) realty?.town=values.getAsString("town")
        if (values.containsKey("type")) realty?.type=values.getAsString("type")
        if (values.containsKey("address")) realty?.address=values.getAsString("address")
        if (values.containsKey("description")) realty?.description=values.getAsString("description")
        if (values.containsKey("numberOfPiece")) realty?.numberOfPiece=values.getAsInteger("numberOfPiece")
        if (values.containsKey("surface")) realty?.surface=values.getAsInteger("surface")
        if (values.containsKey("numberOfPiece")) realty?.price=values.getAsInteger("price")
        if (values.containsKey("entryDate")) realty?.entryDate=values.getAsString("entryDate")
        if (values.containsKey("isAvailable")) realty?.isAvailable=values.getAsBoolean("isAvailable")
        if (values.containsKey("idAgent")) realty?.idAgent=values.getAsInteger("idAgent")
        return realty
    }
}