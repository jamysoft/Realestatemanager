package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import com.openclassrooms.realestatemanager.myContentProvider.RealtyContentProvider
import com.openclassrooms.realestatemanager.myRoomDatabase.RealstateRoomDatabase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyContentProviderTest {

        private var mContentResolver: ContentResolver? = null
        @Before
        fun setUp() {
            Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, RealstateRoomDatabase::class.java)
                .allowMainThreadQueries()
                .build()
            mContentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
        }

        @Test
        fun insertAndGetRealty() {
            //Number of Items before Add
            val cursorInitial: Cursor? = mContentResolver!!.query(
                ContentUris.withAppendedId(RealtyContentProvider.URI_REALTY, ID), null, null, null, null
            )
            var numberItem=cursorInitial?.count
            //  Adding Realty Demo
                mContentResolver!!.insert(RealtyContentProvider.URI_REALTY, generateRealtyItem())
                mContentResolver!!.insert(RealtyContentProvider.URI_REALTY, generateRealtyItem2())

            // TEST getting Realty
            val cursor: Cursor? = mContentResolver!!.query(
                ContentUris.withAppendedId(RealtyContentProvider.URI_REALTY, ID), null, null, null, null
            )
            //Number of Items after Add
            if (numberItem != null) {
                numberItem += 2
            }
            assertThat(cursor, notNullValue())
            assertThat(cursor?.count ,`is`(numberItem))
            assertThat(cursor?.moveToFirst(), `is`(true))
            assertThat(cursor?.getString(cursor.getColumnIndexOrThrow("type")), `is`("Villa"))
            assertThat(cursor?.moveToNext(), `is`(true))
            assertThat(cursor?.getString(cursor.getColumnIndexOrThrow("type")), `is`("Apartment"))
        }
        // ---
        private fun generateRealtyItem(): ContentValues {
            val values = ContentValues()
            values.put("type", "Apartment")
            values.put("price", "2000000")
            values.put("surface", "100")
            values.put("numberOfPiece", "3")
            values.put("description", "belle appartement via provider ")
            values.put("town", "Paris")
            values.put("address", "France")
            values.put("isAvailable", true)
            values.put("entryDate", "2022-11-05")
            values.put("idAgent", 1)
            return values
        }
    private fun generateRealtyItem2(): ContentValues {
        val values = ContentValues()
        values.put("type", "Villa")
        values.put("price", "9000000")
        values.put("surface", "300")
        values.put("numberOfPiece", "6")
        values.put("description", "belle villa via provider ")
        values.put("town", "Yerre")
        values.put("address", "France")
        values.put("isAvailable", true)
        values.put("entryDate", "2022-11-10")
        values.put("idAgent", 1)
        return values
    }


        companion object {
            private const val ID: Long = 1
        }
    }