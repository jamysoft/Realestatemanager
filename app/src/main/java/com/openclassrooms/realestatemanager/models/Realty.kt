package com.openclassrooms.realestatemanager.models

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*TABLE BIEN
 type de bien (appartement, loft, manoir, etc) ;
Le prix du bien (en dollar) ;
La surface du bien (en m2) ;
Le nombre de pièces ;
La description complète du bien ;
Le statut du bien (toujours disponible ou vendu) ;
La date d’entrée du bien sur le marché ;
La date de vente du bien, s’il a été vendu ;

/*@Entity(foreignKeys = arrayOf(ForeignKey(entity = ParentClass::class,
    parentColumns = arrayOf("parentClassColumn"),
    childColumns = arrayOf("childClassColumn"),
    onDelete = ForeignKey.CASCADE)))

 */

 */
// in java : @Entity(foreignKeys = @ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "projectId"))
/*@Entity(tableName ="realty_table" ,
    foreignKeys = [ForeignKey(entity = Agent::class, parentColumns = arrayOf("id_agent"), childColumns = arrayOf("id_agent"), onDelete = ForeignKey.CASCADE)])

 */
@Entity(tableName ="realty_table",  foreignKeys = [ForeignKey(entity = Agent::class, parentColumns = arrayOf("id_agent"), childColumns = arrayOf("id_agent"), onDelete = ForeignKey.CASCADE)])
data class Realty(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_realty") val idRealty: Int?,
    val type:String,
    val price:Int,
    val surface:Int,
    @ColumnInfo(name = "number_piece") val numberOfPiece:Int,
    @ColumnInfo(name = "description_realty") val description:String,
    val town:String,
    val address:String,
    @ColumnInfo(name = "is_available") val isAvailable:Boolean,
    @ColumnInfo(name = "entry_date") val entryDate: String,
    @Nullable
    @ColumnInfo(name = "sale_date") val saleDate: String?,
    @ColumnInfo(name = "id_agent") val idAgent: Int)