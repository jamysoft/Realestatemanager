package com.openclassrooms.realestatemanager.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.models.Shot
import kotlinx.coroutines.flow.Flow

@Dao
interface ShotDao {
    @Query("SELECT * FROM shot_table")
    fun getAllShot(): Flow<List<Shot>>

    @Query("SELECT * FROM shot_table  WHERE  id_realty = :idRealty")
    fun getAllShotByIdRealty(idRealty: Int): Flow<List<Shot>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shot: Shot): Long

    @Update
    suspend fun updateShot(shot: Shot)

    /*
        //suspend meaning??
        suspend fun deleteAll()
    */
    @Query("DELETE FROM shot_table WHERE id_realty = :id ")
    suspend fun deleteAllShotOfRealty(id: Int)

}