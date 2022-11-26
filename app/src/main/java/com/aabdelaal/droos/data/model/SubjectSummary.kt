package com.aabdelaal.droos.data.model

import com.aabdelaal.droos.data.dto.SubjectDTO

data class SubjectSummary(
    val subject: SubjectDTO,
    val pendingCount: Int = 0,
    val paidCount: Int = 0
) {
}