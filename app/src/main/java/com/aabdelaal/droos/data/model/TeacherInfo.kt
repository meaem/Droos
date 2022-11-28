package com.aabdelaal.droos.data.model

import java.io.Serializable


data class TeacherInfo(
    var name: String = "",
    var phone: String = "",
//    @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
    var active: Boolean = true,
    var remoteID: String? = null,
    //local ID
    var id: Long = 0
) : Serializable