package com.aabdelaal.droos.data.model


import java.io.Serializable
import java.util.*

data class Dars(
    val subject: Subject? = null,
    var date: Date = Date(),
    var duration: Int = 0,
    var remoteID: String? = null,
    val id: Long = 0
) : Serializable