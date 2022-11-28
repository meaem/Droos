package com.aabdelaal.droos.data.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Dars")
data class DarsEntity(
    @ColumnInfo(name = "subject")
    val subject: SubjectEntity,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "duration")
    val duration: Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0

)