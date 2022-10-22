package com.openclassrooms.realestatemanager.repository

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.dao.ShotDao
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.models.Shot
import kotlinx.coroutines.flow.Flow

class ShotRepository(private val shotDao: ShotDao) {

    val getAllShot: Flow<List<Shot>> = shotDao.getAllShot()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
     fun getAllShotByIdRealty(id:Int): Flow<List<Shot>> = shotDao.getAllShotByIdRealty(id)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(shot: Shot):Long{
        return shotDao.insert(shot)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateShot(shot:Shot) {
        shotDao.updateShot(shot)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend  fun deleteAllShotOfRealty(id:Int){
        shotDao.deleteAllShotOfRealty(id)
    }


}