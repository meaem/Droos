package com.aabdelaal.droos.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aabdelaal.droos.data.dto.TeacherInfoDTO

/**
 * Data Access Object for the droos tables.
 */
@Dao
interface DroosDao {
    /**
     * @return all teachers.
     */
    @Query("SELECT * FROM TeacherInfo")
    fun getTeachers(): LiveData<List<TeacherInfoDTO>>

    /**
     * @return all teachers.
     */
    @Query("SELECT * FROM TeacherInfo where isActive=:isActive")
    fun getTeachersByStatus(isActive: Boolean): LiveData<List<TeacherInfoDTO>>


    /**
     * @param teacherId the id of the Teacher Info
     * @return the teacherInfo object with the id
     */
    @Query("SELECT * FROM teacherinfo where id = :teacherId")
    suspend fun getTecherInfoById(teacherId: Int): TeacherInfoDTO?

    /**
     * Insert a teacher info in the database. If the teacher already exists, replace it.
     *
     * @param teacherInfoDTO the teacherInfo to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTeacherInfo(teacherInfo: TeacherInfoDTO): Long

    /**
     * Delete all teachers.
     */
    @Query("DELETE FROM TeacherInfo")
    suspend fun deleteAllTeacherInfo()

    /**
     * Delete teacher by ID.
     */
    @Query("DELETE FROM TeacherInfo where id=:teacherId")
    suspend fun deleteTeacherInfo(teacherId: Int)

}