package com.openclassrooms.realestatemanager.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.models.Realty
import kotlinx.coroutines.flow.Flow

@Dao
interface RealtyDao {
    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    //flow as livedata in java
    @Query("SELECT * FROM realty_table ORDER BY id_realty DESC")
    fun getAllRealtyNewerToOlder(): Flow<List<Realty>>

    @Query("SELECT * FROM realty_table WHERE id_realty = :id")
    fun getRealtyById(id:Int): Flow<Realty>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(realty: Realty):Long

    @Update
    fun updateRealty(Realty: Realty)


    @Query("UPDATE realty_table SET is_available = 0")
    suspend fun updateStatusRealty()

    @Query("DELETE FROM realty_table")
    //suspend meaning??
    suspend fun deleteAll()

    @Query("DELETE FROM realty_table WHERE id_realty = :id ")
    suspend fun deleteRealtyById(id: Int)


}