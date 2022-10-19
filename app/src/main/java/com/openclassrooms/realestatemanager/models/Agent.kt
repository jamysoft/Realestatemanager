package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
TABLE AGENT IMMOBILIER
L'agent immobilier en charge de ce bien.
 */
@Entity(tableName = "agent_table")
data class Agent(
            @PrimaryKey(autoGenerate = true)
            @ColumnInfo(name="id_agent") var idAgent :Int?,
            @ColumnInfo(name="first_name") var firstName:String,
            @ColumnInfo(name="last_name") var lastName:String,
            @ColumnInfo(name="email") var email:String,
            @ColumnInfo(name="phone") var phone:String
            )
