package com.aabdelaal.droos.data.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Dars")
data class DarsEntity(


    @ColumnInfo(name = "date") // date time
    val date: Date = Date(),

    @ColumnInfo(name = "duration")
    val duration: Int = 60, // mintues

    @ColumnInfo(name = "remote_id")
    var remoteID: String?,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "current_subject_id")
    val subjectId: Long?,

    )