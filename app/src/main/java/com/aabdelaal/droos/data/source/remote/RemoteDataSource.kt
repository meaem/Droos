package com.aabdelaal.droos.data.source.remote

import androidx.lifecycle.LiveData
import com.aabdelaal.droos.data.source.remote.models.RemoteSubject
import com.aabdelaal.droos.data.source.remote.models.RemoteTeacherInfo


/**
 * Main entry point for accessing droos data.
 */
interface RemoteDataSource {
    suspend fun getTeachers(): Result<LiveData<List<RemoteTeacherInfo>>>
    fun getTeachersByStatus(isActive: Boolean): Result<LiveData<List<RemoteTeacherInfo>>>
    suspend fun addTeacherInfo(teacherInfo: RemoteTeacherInfo): String
    suspend fun updateTeacherInfo(teacherInfo: RemoteTeacherInfo)
    suspend fun getTeacherInfoById(id: String): Result<RemoteTeacherInfo>
    suspend fun deleteAllTeachers()
    suspend fun deleteTeacherInfo(id: String)


    suspend fun getSubjects(): Result<LiveData<List<RemoteSubject>>>
    suspend fun addSubject(subject: RemoteSubject): String
    suspend fun updateSubject(subject: RemoteSubject)

    suspend fun deleteAllSubjects()
    suspend fun deleteSubject(id: String)

}