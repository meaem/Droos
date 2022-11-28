package com.aabdelaal.droos.data.model

import com.aabdelaal.droos.data.source.local.entities.SubjectEntity
import java.io.Serializable

data class SubjectSummary(
    val subject: SubjectEntity,
    val pendingCount: Int = 0,
    val paidCount: Int = 0
) : Serializable