/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.openclassrooms.realestatemanager.repository
import androidx.annotation.WorkerThread
import androidx.room.Query
import com.openclassrooms.realestatemanager.dao.RealtyDao
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.models.RealtyItem
import com.openclassrooms.realestatemanager.models.Shot
import kotlinx.coroutines.flow.Flow

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
 class RealtyRepository(private val realtyDao: RealtyDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.

    val getAllRealtyNewerToOlder: Flow<List<Realty>> = realtyDao.getAllRealtyNewerToOlder();

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(realty: Realty):Long{
         return realtyDao.insert(realty)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: Int) {
        realtyDao.deleteRealtyById(id)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateRealty(realty:Realty) {
        realtyDao.updateRealty(realty)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateStatusRealty(id:Int) {
        realtyDao.updateStatusRealty(id)
    }
    val  getAllRealtyItem:Flow<List<RealtyItem>> = realtyDao.getAllRealtyItem()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getRealtyById(id:Int): Flow<Realty> = realtyDao.getRealtyById(id)


}
