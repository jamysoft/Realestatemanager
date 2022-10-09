

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
@Database(entities = [Realty::class,Agent::class,Shot::class,InterestPoints::class,RealtyInterestPoints::class], version = 1)
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



           var realty = Realty("apart",2000000,10,3,"belle appart","Paris", "maroc ",false,Date().toString(),2)
           println(realty.toString())
            realtyDao.insert(realty)
            var realty2 = Realty("ferme",2000000,10,3,"ferme mkhayra tbarklah ","Paris", "maroc ",false,Date().toString(),2)
            println(realty2.toString())
            realtyDao.insert(realty2)


        }
    }
}