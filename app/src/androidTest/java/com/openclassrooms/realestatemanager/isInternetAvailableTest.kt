package com.openclassrooms.realestatemanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.utils.Utils.Companion.isInternetAvailable
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class InternetAvailableTest {
    lateinit var context: Context

   @Before
    fun setUp() {
         context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun isInternetAvailableTestisCorrect() {
     assertThat(isInternetAvailable(context), `is`(true))
    }

}