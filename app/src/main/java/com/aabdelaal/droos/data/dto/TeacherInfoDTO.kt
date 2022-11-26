package com.aabdelaal.droos.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

@Entity(tableName = "TeacherInfo")
data class TeacherInfoDTO(

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "isActive")
    @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
    val isActive: Boolean = true,

    @ColumnInfo(name = "remote_id")
    @DocumentId
    var remoteID: String?,


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0
)