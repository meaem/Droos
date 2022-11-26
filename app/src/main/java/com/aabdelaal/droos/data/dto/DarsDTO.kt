package com.aabdelaal.droos.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Dars")
data class DarsDTO(
    @ColumnInfo(name = "subject")
    val subject: SubjectDTO,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "duration")
    val duration: Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null

)