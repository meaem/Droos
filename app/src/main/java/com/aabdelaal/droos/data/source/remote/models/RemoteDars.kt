package com.aabdelaal.droos.data.source.remote.models


import com.google.firebase.firestore.DocumentId
import java.util.*

data class RemoteDars(
    val subject: RemoteSubject,
    val date: Date,
    val duration: Int,

    @DocumentId
    var remoteID: String?,

    val id: Long = 0

)