package com.aabdelaal.droos.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aabdelaal.droos.data.source.local.entities.TeacherInfoEntity

/**
 * The Room Database that contains the droos tables.
 */
@Database(entities = [TeacherInfoEntity::class], version = 2, exportSchema = false)
abstract class DroosDatabase : RoomDatabase() {

    abstract fun droosDao(): DroosDao
}