package com.aabdelaal.droos.data.source.remote.models


import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude

data class RemoteSubject(

    val name: String,

    @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
    val isActive: Boolean = true,


    @DocumentId
    var remoteID: String?,

    val id: Long = 0,


    val currentTeacherInfoId: String?

) {
    @Exclude
    var currentTeacher: RemoteTeacherInfo? = null
}