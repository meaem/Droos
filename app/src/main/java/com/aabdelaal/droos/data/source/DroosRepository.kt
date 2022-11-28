package com.aabdelaal.droos.data.source

import androidx.lifecycle.LiveData
import com.aabdelaal.droos.data.model.Subject
import com.aabdelaal.droos.data.model.TeacherInfo

interface DroosRepository {
    fun getTeachers(): Result<LiveData<List<TeacherInfo>>>
    fun getTeachersByStatus(isActive: Boolean): Result<LiveData<List<TeacherInfo>>>
    suspend fun saveTeacherInfo(teacherInfo: TeacherInfo)
    suspend fun getTeacherInfoById(id: Long): Result<TeacherInfo>
    suspend fun deleteAllTeachers()
    suspend fun deleteTeacherInfo(id: Long)


    fun getSubjects(): Result<LiveData<List<Subject>>>
    suspend fun saveSubject(subject: Subject)
    suspend fun getSubjectById(id: Long): Result<Subject>
    suspend fun deleteAllSubjects()
    suspend fun deleteSubject(id: Long)


}