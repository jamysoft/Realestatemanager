package com.openclassrooms.realestatemanager.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.models.Agent
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentDao {
    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM agent_table")
    fun getAllAgent(): Flow<List<Agent>>

    @Query("SELECT * FROM agent_table where id_agent = :id_agent")
    fun getAgentById(id_agent: Int): Flow<Agent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(agent: Agent)

    @Query("DELETE FROM agent_table")
    suspend fun deleteAllAgent()

    @Query("DELETE FROM agent_table where id_agent = :id_agent")
    suspend fun deleteAgentById(id_agent: Int)
}