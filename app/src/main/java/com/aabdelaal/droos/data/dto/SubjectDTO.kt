package com.aabdelaal.droos.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Subject")
data class SubjectDTO(
    @ColumnInfo(name = "name")
    val name: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null
) {
}