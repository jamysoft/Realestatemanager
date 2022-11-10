package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.utils.Utils.Companion.arrondiNumber
import com.openclassrooms.realestatemanager.utils.Utils.Companion.convertDollarToEuro
import com.openclassrooms.realestatemanager.utils.Utils.Companion.convertEuroToDolar
import com.openclassrooms.realestatemanager.utils.Utils.Companion.getTodayDate
import com.openclassrooms.realestatemanager.utils.Utils.Companion.getTodayDateFr
import org.junit.Test

import org.junit.Assert.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class UtilsUnitTest {

    private val numberToArrondi=20.2545854F


    @Test
    fun getTodayDate_isCorrect() {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        var   todayDate=   dateFormat.format(Date())
        assertEquals(todayDate, getTodayDate())
    }
    @Test
    fun getTodayDateFr_isCorrect() {
        val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        var   todayDateFr=   dateFormat.format(Date())
        assertEquals(todayDateFr, getTodayDateFr())
    }

    @Test
    fun convertDollarToEuro_isCorrect() {
        assertEquals(812, convertDollarToEuro(1000))
    }
    @Test
    fun convertEuroToDollar_isCorrect() {
        assertEquals(1000, convertEuroToDolar(812))
    }

    @Test
    fun arrondiNumber_isCorrect(){
        assertEquals("20,25", arrondiNumber(numberToArrondi))
    }
}