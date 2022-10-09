package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*
  Les points d’intérêts à proximité (école, commerces, parc, etc) ;
 */
@Entity(tableName ="nearbyInterestPoints_table")
data class InterestPoints(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_Points") var idPoints: Int,
    @ColumnInfo(name = "name_point") var namePoint:String,
   ) {
    constructor(namePoint: String):this(0,namePoint )
}