package com.aabdelaal.droos.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aabdelaal.droos.data.source.local.entities.DarsEntity
import com.aabdelaal.droos.data.source.local.entities.SubjectEntity
import com.aabdelaal.droos.data.source.local.entities.TeacherInfoEntity

/**
 * The Room Database that contains the droos tables.
 */
@Database(
    entities = [TeacherInfoEntity::class, SubjectEntity::class, DarsEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DroosDatabase : RoomDatabase() {

    abstract fun droosDao(): DroosDao
}