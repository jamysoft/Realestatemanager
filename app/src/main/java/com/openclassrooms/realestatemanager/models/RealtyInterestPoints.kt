package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "realty_nearbyInterestPoints_table")
data class RealtyInterestPoints(
    @PrimaryKey(autoGenerate = true) var id_realty_points: Int?,
    @ColumnInfo(name = "id_point") var idPoint: Int,
    @ColumnInfo(name = "id_realty") var idRealty: Int
)