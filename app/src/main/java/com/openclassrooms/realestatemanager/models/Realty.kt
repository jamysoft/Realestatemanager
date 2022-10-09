package com.openclassrooms.realestatemanager.models

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
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
@Entity(tableName ="realty_table")
data class Realty(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_realty") var idRealty: Int,
    @ColumnInfo(name = "type") var type:String,
    @ColumnInfo(name = "price") var price:Int,
    @ColumnInfo(name = "surface") var surface:Int,
    @ColumnInfo(name = "number_piece") var numberOfPiece:Int,
    @ColumnInfo(name = "description_realty") var description:String,
    @ColumnInfo(name = "town") var town:String,
    @ColumnInfo(name = "address") var address:String,
    @ColumnInfo(name = "is_available") var isAvailable:Boolean,
    @ColumnInfo(name = "entry_date") var entryDate: String,
    @Nullable
    @ColumnInfo(name = "sale_date") var saleDate: String?,
    @ColumnInfo(name = "id_agent") var idAgent: Int){
   //Constructeur secondaire
    constructor(type: String,price:Int,surface:Int,numberOfPiece:Int,description: String,town:String,
                address: String,isavailable: Boolean,entryDate: String,idAgent: Int)
            :this(0,type,price,surface,numberOfPiece,description,town,address,isavailable,entryDate,
        entryDate,idAgent)


}