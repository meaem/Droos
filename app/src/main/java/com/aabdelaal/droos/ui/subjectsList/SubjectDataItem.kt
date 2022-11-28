package com.aabdelaal.droos.ui.subjectsList

import com.aabdelaal.droos.data.model.TeacherInfo
import java.io.Serializable

/**
 * data class acts as a data mapper between the DB and the UI
 */
data class SubjectDataItem(
    var name: String = "",
    var teacher: TeacherInfo? = null,
    var isActive: Boolean = false,
    var remoteId: String? = null,
    var id: Int = 0

) : Serializable


