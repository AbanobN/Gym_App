package com.example.gym_app.gyms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Gym::class],
    version = 1,
    exportSchema = false
)
abstract class GymsDatabase : RoomDatabase() {
    abstract val doa:GymsDao

    companion object{
        @Volatile
        private var daoInstance: GymsDao? = null

        private fun buildDatabase(context: Context) : GymsDatabase = Room.databaseBuilder(
                context.applicationContext,
                GymsDatabase::class.java,
                "gyms_database"
            )
            .fallbackToDestructiveMigrationFrom()
            .build()

        fun getDaoInstance(context: Context) : GymsDao{
            synchronized(this){
                if(daoInstance == null)
                {
                    daoInstance = buildDatabase(context).doa
                }
                return daoInstance as GymsDao
            }
        }
    }
}