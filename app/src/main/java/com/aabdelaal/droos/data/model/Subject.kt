package com.aabdelaal.droos.data.model

data class Subject(

    val name: String = "",
    var teacher: TeacherInfo? = null,
    @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
    val isActive: Boolean = true,
    var remoteID: String? = null,
    val id: Int = 0

)