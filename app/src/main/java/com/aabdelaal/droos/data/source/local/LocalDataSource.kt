package com.aabdelaal.droos.data.source.local

import androidx.lifecycle.LiveData
import com.aabdelaal.droos.data.model.Dars
import com.aabdelaal.droos.data.model.Subject
import com.aabdelaal.droos.data.model.TeacherInfo


/**
 * Main entry point for accessing droos data.
 */
interface LocalDataSource {
    fun getTeachers(): Result<LiveData<List<TeacherInfo>>>
    fun getTeachersByStatus(isActive: Boolean): Result<LiveData<List<TeacherInfo>>>
    suspend fun saveTeacherInfo(teacherInfo: TeacherInfo): Long
    suspend fun getTeacherInfoById(id: Long): Result<TeacherInfo>
    suspend fun deleteAllTeachers()
    suspend fun deleteTeacherInfo(id: Long)

    fun getSubjects(): Result<LiveData<List<Subject>>>
    suspend fun getSubjectById(id: Long): Result<Subject>
    suspend fun saveSubject(subject: Subject): Long
    suspend fun deleteAllSubjects()
    suspend fun deleteSubject(id: Long)


    fun getDroos(): Result<LiveData<List<Dars>>>
    suspend fun getDarsById(id: Long): Result<Dars>
    suspend fun saveDars(subject: Dars): Long
    suspend fun deleteAllDroos()
    suspend fun deleteDars(id: Long)
}