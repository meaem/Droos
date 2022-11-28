package com.aabdelaal.droos.data.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Subject")
data class SubjectEntity(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "isActive")
    @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
    val isActive: Boolean = true,

    @ColumnInfo(name = "remote_id")

    var remoteID: String?,


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,


    @ColumnInfo(name = "current_teacher_id")
    val currentTeacherInfoId: Long? = null,


    ) {

}