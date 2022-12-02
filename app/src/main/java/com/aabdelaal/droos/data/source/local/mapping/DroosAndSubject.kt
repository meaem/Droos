package com.aabdelaal.droos.data.source.local.mapping

import androidx.room.Embedded
import androidx.room.Relation
import com.aabdelaal.droos.data.source.local.entities.DarsEntity
import com.aabdelaal.droos.data.source.local.entities.SubjectEntity

class DroosAndSubject(
    @Embedded
    val dars: DarsEntity,

    @Relation(
        parentColumn = "current_subject_id",
        entityColumn = "id"
    )
    val subject: SubjectEntity? = null


)