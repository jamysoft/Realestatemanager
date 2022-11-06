package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.utils.Utils.Companion.arrondiNumber
import com.openclassrooms.realestatemanager.utils.Utils.Companion.convertDollarToEuro
import com.openclassrooms.realestatemanager.utils.Utils.Companion.getTodayDate
import org.junit.Test

import org.junit.Assert.*

class UtilsUnitTest {

    private val todayDate="2022-11-05"
    private val numberToArrondi=20.2545854F

    @Test
    fun getTodayDate_isCorrect() {
        assertEquals(todayDate, getTodayDate())
    }

    @Test
    fun convertDollarToEuro_isCorrect() {
        assertEquals(8, convertDollarToEuro(10))
    }

    @Test
    fun arrondiNumber_isCorrect(){
        assertEquals("20,25", arrondiNumber(numberToArrondi))
    }
}