package com.aabdelaal.droos.data.source.remote.models


import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import java.util.*

data class RemoteDars(
    val date: Date,
    val duration: Int,

    @DocumentId
    var remoteID: String?,

    val id: Long = 0,


    val currentSubjectId: String?

) {
    @Exclude
    var currentSubject: RemoteSubject? = null
}