package com.aabdelaal.droos.data.source

import androidx.lifecycle.LiveData
import com.aabdelaal.droos.data.dto.TeacherInfoDTO

interface DroosRepository {
    fun getTeachers(): Result<LiveData<List<TeacherInfoDTO>>>
    fun getTeachersByStatus(isActive: Boolean): Result<LiveData<List<TeacherInfoDTO>>>
    suspend fun saveTeacherInfo(teacherInfo: TeacherInfoDTO)
    suspend fun getTeacherInfoById(id: Int): Result<TeacherInfoDTO>
    suspend fun deleteAllTeachers()
    suspend fun deleteTeacherInfo(id: Int)


}