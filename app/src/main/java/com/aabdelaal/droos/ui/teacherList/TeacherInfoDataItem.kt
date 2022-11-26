package com.aabdelaal.droos.ui.teacherList

import java.io.Serializable

/**
 * data class acts as a data mapper between the DB and the UI
 */
data class TeacherInfoDataItem(
    var name: String = "",
    var phone: String = "",
    var isActive: Boolean = false,
    var remoteId: String? = null,
    var id: Int = 0

) : Serializable


