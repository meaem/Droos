package com.aabdelaal.droos.data.source.remote

import androidx.lifecycle.LiveData
import com.aabdelaal.droos.data.dto.TeacherInfoDTO


/**
 * Main entry point for accessing droos data.
 */
interface RemoteDataSource {
    fun getTeachers(): Result<LiveData<List<TeacherInfoDTO>>>
    fun getTeachersByStatus(isActive: Boolean): Result<LiveData<List<TeacherInfoDTO>>>
    suspend fun addTeacherInfo(teacherInfo: TeacherInfoDTO): String
    suspend fun updateTeacherInfo(teacherInfo: TeacherInfoDTO)
    suspend fun getTeacherInfoById(id: String): Result<TeacherInfoDTO>
    suspend fun deleteAllTeachers()
    suspend fun deleteTeacherInfo(id: String)
}