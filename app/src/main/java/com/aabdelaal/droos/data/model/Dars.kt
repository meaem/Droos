package com.aabdelaal.droos.data.model


import java.io.Serializable
import java.util.*

data class Dars(
    val subject: Subject,
    val date: Date,
    val duration: Int,
    var remoteID: String?,
    val id: Long = 0
) : Serializable