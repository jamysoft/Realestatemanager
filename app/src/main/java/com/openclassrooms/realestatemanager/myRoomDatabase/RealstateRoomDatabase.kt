

package com.openclassrooms.realestatemanager.myRoomDatabase

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.dao.AgentDao
import com.openclassrooms.realestatemanager.dao.RealtyDao
import com.openclassrooms.realestatemanager.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [Realty::class,Agent::class,Shot::class,InterestPoints::class,RealtyInterestPoints::class], version = 2)
abstract class RealstateRoomDatabase : RoomDatabase() {

    abstract fun realtyDao(): RealtyDao
   abstract fun agentDao(): AgentDao

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
                         populateDatabase(database.realtyDao(),database.agentDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(realtyDao: RealtyDao,agentDao: AgentDao) {

            realtyDao.deleteAll()
            var agent=Agent("Jo","Jack","jak@gmail.com","06524547")
            agentDao.insert(agent)
            var agent2=Agent("Mizo","Ezra","ezra@gmail.com","06854547")
            agentDao.insert(agent2)



           var realty = Realty("Apartment",2000000,10,3,"belle appartement","Paris", "maroc ",false,Date().toString(),2)
            realtyDao.insert(realty)
            var realty2 = Realty("Maison",2000000,10,3,"magnifique Maison ! ","Yerre", "maroc ",false,Date().toString(),2)
            realtyDao.insert(realty2)
            var realty3 = Realty("Duplex",2000000,10,3,"Jolie Duplex ","Brunoy", "maroc ",false,Date().toString(),2)
            realtyDao.insert(realty3)
            var realty4 = Realty("Studio",2000000,10,3,"Studio bien déménagé! ","Nice", "maroc ",false,Date().toString(),2)
            realtyDao.insert(realty4)
            var realty5 = Realty("Apartment",2000000,10,3,"belle appartement","Montgeron", "maroc ",false,Date().toString(),2)
            realtyDao.insert(realty5)
            var realty6 = Realty("Ferme",2000000,10,3,"Magnifique Ferme ! ","Saint Maurice", "maroc ",false,Date().toString(),2)
            realtyDao.insert(realty6)
            var realty7 = Realty("Villa",2000000,10,3,"Grande magnifique Villa ","Créteil", "maroc ",false,Date().toString(),2)
            realtyDao.insert(realty7)
            var realty8= Realty("Studio",2000000,10,3,"Studio bien déménagé !","Vincenne", "maroc ",false,Date().toString(),2)
            realtyDao.insert(realty8)
            var realty9 = Realty("Maison",2000000,10,3,"Grand maison !","Paris", "Paris ",false,Date().toString(),2)
            realtyDao.insert(realty9)
            var realty10 = Realty("Duplex",2000000,10,3,"Jolie Duplex! ","Paris", "Bordeaux ",false,Date().toString(),2)
            realtyDao.insert(realty10)


        }
    }
}