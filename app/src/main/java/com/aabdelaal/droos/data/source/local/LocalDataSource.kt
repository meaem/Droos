package com.aabdelaal.droos.data.source.local

import androidx.lifecycle.LiveData
import com.aabdelaal.droos.data.dto.TeacherInfoDTO


/**
 * Main entry point for accessing droos data.
 */
interface LocalDataSource {
    fun getTeachers(): Result<LiveData<List<TeacherInfoDTO>>>
    fun getTeachersByStatus(isActive: Boolean): Result<LiveData<List<TeacherInfoDTO>>>
    suspend fun saveTeacherInfo(teacherInfo: TeacherInfoDTO): Long
    suspend fun getTeacherInfoById(id: Int): Result<TeacherInfoDTO>
    suspend fun deleteAllTeachers()
    suspend fun deleteTeacherInfo(id: Int)
}