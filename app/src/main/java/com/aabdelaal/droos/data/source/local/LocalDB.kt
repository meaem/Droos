package com.aabdelaal.droos.data.source.local

import android.content.Context
import androidx.room.Room


/**
 * Singleton class that is used to create a droos db
 */
object LocalDB {

    /**
     * static method that creates a droos class and returns the DAO of droos
     */
    fun createDroosDao(context: Context): DroosDao {
        return Room.databaseBuilder(
            context.applicationContext,
            DroosDatabase::class.java, "droos.db"
        )
            .fallbackToDestructiveMigration()
            .build().droosDao()
    }

}