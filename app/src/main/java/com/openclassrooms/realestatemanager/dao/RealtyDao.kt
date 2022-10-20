package com.openclassrooms.realestatemanager.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.models.RealtyItem
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


    @Query("UPDATE realty_table SET is_available = 0 WHERE id_realty= :id")
    suspend fun updateStatusRealty(id:Int)

    @Query("DELETE FROM realty_table")
    //suspend meaning??
    suspend fun deleteAll()

    @Query("DELETE FROM realty_table WHERE id_realty = :id ")
    suspend fun deleteRealtyById(id: Int)

   /* @Query("SELECT realty_table .id_realty,type,price,surface,number_piece,town,address,is_available,entry_date,sale_date,shot FROM  realty_table INNER JOIN SHOT_TABLE ON realty_table.id_realty=SHOT_TABLE.id_realty")
    fun getAllRealtyItem():Flow<List<RealtyItem>>

    */
   @Query("SELECT realty_table .id_realty as idRealty,type,price,surface,number_piece as numberOfPiece,town," +
           "description_realty as description ,address,is_available as isAvailable,entry_date as entryDate" +
           ",sale_date as saleDate,shot FROM  realty_table " +
           "INNER JOIN SHOT_TABLE ON realty_table.id_realty=SHOT_TABLE.id_realty " +
           "where description_shot=\"MainShot\"")
   fun getAllRealtyItem():Flow<List<RealtyItem>>

}