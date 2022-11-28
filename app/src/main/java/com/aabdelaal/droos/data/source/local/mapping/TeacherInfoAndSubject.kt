package com.aabdelaal.droos.data.source.local.mapping

import androidx.room.Embedded
import androidx.room.Relation
import com.aabdelaal.droos.data.source.local.entities.SubjectEntity
import com.aabdelaal.droos.data.source.local.entities.TeacherInfoEntity

class TeacherInfoAndSubject(
    @Embedded
    val subject: SubjectEntity,

    @Relation(
        parentColumn = "current_teacher_id",
        entityColumn = "id"
    )
    val teacher: TeacherInfoEntity? = null


)