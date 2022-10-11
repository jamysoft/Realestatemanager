package com.openclassrooms.realestatemanager.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob
import java.sql.Types.BLOB

/*
TABLE PHOTO
Au moins une photo, avec une description associée.
Vous devez gérer le cas où plusieurs photos sont présentes pour un bien
 La photo peut être récupérée depuis la galerie photos du téléphone, ou prise directement avec l'équipement ;
L’adresse du bien ;
 */
@Entity(tableName ="shot_table")
data class Shot(
    @ColumnInfo(name="id_shot")
    @PrimaryKey(autoGenerate = true) var idShot :Int?,
    @ColumnInfo(name="description_shot") var descriptionShot:String,
    @ColumnInfo(name="shot", typeAffinity = BLOB) var shot:ByteArray
) {
    constructor(descriptionShot: String,shot: ByteArray):this(null,descriptionShot,shot)
}