package com.aabdelaal.droos.data.source.local

import androidx.lifecycle.LiveData
import com.aabdelaal.droos.data.model.Subject
import com.aabdelaal.droos.data.model.TeacherInfo


/**
 * Main entry point for accessing droos data.
 */
interface LocalDataSource {
    fun getTeachers(): Result<LiveData<List<TeacherInfo>>>
    fun getTeachersByStatus(isActive: Boolean): Result<LiveData<List<TeacherInfo>>>
    suspend fun saveTeacherInfo(teacherInfo: TeacherInfo): Long
    suspend fun getTeacherInfoById(id: Int): Result<TeacherInfo>
    suspend fun deleteAllTeachers()
    suspend fun deleteTeacherInfo(id: Int)

    fun getSubjects(): Result<LiveData<List<Subject>>>
    suspend fun saveSubject(subject: Subject): Long


}