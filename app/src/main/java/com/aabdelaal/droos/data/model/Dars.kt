package com.aabdelaal.droos.data.model


import java.util.*

data class Dars(
    val subject: Subject,
    val date: Date,
    val duration: Int,
    var remoteID: String?,
    val id: Int? = null
)