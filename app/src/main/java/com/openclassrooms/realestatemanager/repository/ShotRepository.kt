package com.openclassrooms.realestatemanager.repository

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.dao.ShotDao
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.models.Shot
import kotlinx.coroutines.flow.Flow

class ShotRepository(private val shotDao: ShotDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllShotByIdRealty(id:Int): Flow<List<Shot>> = shotDao.getAllShotByIdRealty(id)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(shot: Shot):Long{
        return shotDao.insert(shot)
    }


}