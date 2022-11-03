package com.openclassrooms.realestatemanager.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*
TABLE PHOTO
Au moins une photo, avec une description associée.
Vous devez gérer le cas où plusieurs photos sont présentes pour un bien
 La photo peut être récupérée depuis la galerie photos du téléphone,
 ou prise directement avec l'équipement ;
 */
@Entity(
    tableName = "shot_table",
    foreignKeys = [ForeignKey(
        entity = Realty::class,
        parentColumns = arrayOf("id_realty"),
        childColumns = arrayOf("id_realty"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Shot(
    @ColumnInfo(name = "id_shot")
    @PrimaryKey(autoGenerate = true) var idShot: Int?,
    @ColumnInfo(name = "description_shot") var descriptionShot: String,
    //  @ColumnInfo(typeAffinity = BLOB) var shot:ByteArray,
    var shot: Bitmap,
    @ColumnInfo(name = "id_realty") var idRealty: Int
)