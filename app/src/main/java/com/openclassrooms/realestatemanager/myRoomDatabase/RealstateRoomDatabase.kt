

package com.openclassrooms.realestatemanager.myRoomDatabase

import android.R
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.dao.AgentDao
import com.openclassrooms.realestatemanager.dao.RealtyDao
import com.openclassrooms.realestatemanager.dao.ShotDao
import com.openclassrooms.realestatemanager.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [Realty::class,Agent::class,Shot::class,InterestPoints::class,RealtyInterestPoints::class], version = 4)
@TypeConverters(Converters::class)
abstract class RealstateRoomDatabase : RoomDatabase() {

    abstract fun realtyDao(): RealtyDao
    abstract fun agentDao(): AgentDao
    abstract fun shotDao(): ShotDao

// static WordRoomDatabase getDatabase(final Context context) {...}
    companion object {
        @Volatile
        private var INSTANCE: RealstateRoomDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): RealstateRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            //If the expression to the left of ?: is not null it returns the expression to the right.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, RealstateRoomDatabase::class.java, "Post_database3")
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(RealstateDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }

        }

        private class RealstateDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let {
                        database -> scope.launch(Dispatchers.IO) {
                         populateDatabase(database.realtyDao(),database.agentDao(),database.shotDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(realtyDao: RealtyDao, agentDao: AgentDao, shotDao: ShotDao) {

            realtyDao.deleteAll()
            var agent=Agent(null,"Jo","Jack","jak@gmail.com","06524547")
            agentDao.insert(agent)
            var agent2=Agent(null,"Mizo","Ezra","ezra@gmail.com","06854547")
            agentDao.insert(agent2)


          //add realty 1 and her shot
           var realty = Realty(null,"Apartment",2000000,10,3,"belle appartement","Paris", "maroc ",true,Date().toString(),null,2)
            realtyDao.insert(realty)

               /* var shot = Shot(null, "MainShot", shotImg, 1)
                shotDao.insert(shot)
                */

            //add realty 2 and her shot
            var realty2 = Realty(null,"Maison",2000000,10,3,"magnifique Maison ! ","Yerre", "maroc ",true,Date().toString(),null,2)
            realtyDao.insert(realty2)
           /* shot.idRealty=2
            shotDao.insert(shot)*/

            //add realty 3 and her shot
            var realty3 = Realty(null,"Duplex",2000000,10,3,"Jolie Duplex ","Brunoy", "maroc ",true,Date().toString(),null,2)
            realtyDao.insert(realty3)
           /*
           shot.idRealty=3
            shotDao.insert(shot)
            */

            //add realty 4 and her shot
            var realty4 = Realty(null,"Studio",2000000,10,3,"Studio bien déménagé! ","Nice", "maroc ",true,Date().toString(),null,2)
            realtyDao.insert(realty4)
          /*  shot.idRealty=4
            shotDao.insert(shot)
            */

            //add realty 5 and her shot
            var realty5 = Realty(null,"Apartment",2000000,10,3,"belle appartement","Montgeron", "maroc ",true,Date().toString(),null,2)
            realtyDao.insert(realty5)
           /* shot.idRealty=5
            shotDao.insert(shot)
            */

            //add realty 6 and her shot
            var realty6 = Realty(null,"Ferme",2000000,10,3,"Magnifique Ferme ! ","Saint Maurice", "maroc ",true,Date().toString(),null,2)
            realtyDao.insert(realty6)
           /* shot.idRealty=6
            shotDao.insert(shot)
            */

            //add realty 7 and her shot
            var realty7 = Realty(null,"Villa",2000000,10,3,"Grande magnifique Villa ","Créteil", "maroc ",true,Date().toString(),null,2)
            realtyDao.insert(realty7)
            /*shot.idRealty=7
            shotDao.insert(shot)
            */

            //add realty =8 and her shot
            var realty8= Realty(null,"Studio",2000000,10,3,"Studio bien déménagé !","Vincenne", "maroc ",true,Date().toString(),null,2)
            realtyDao.insert(realty8)
          /*  shot.idRealty=8
            shotDao.insert(shot)
            */

            //add realty 9 and her shot
            var realty9 = Realty(null,"Maison",2000000,10,3,"Grand maison !","Paris", "Paris ",true,Date().toString(),null,2)
            realtyDao.insert(realty9)
           /* shot.idRealty=9
            shotDao.insert(shot)
            */

            //add realty 10 and her shot
            var realty10 = Realty(null,"Duplex",2000000,10,3,"Jolie Duplex! ","Paris", "Bordeaux ",true,Date().toString(),null,2)
            realtyDao.insert(realty10)
           /* shot.idRealty=10
            shotDao.insert(shot)
            */
        }
    }
}