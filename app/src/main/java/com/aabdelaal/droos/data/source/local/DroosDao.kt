package com.aabdelaal.droos.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aabdelaal.droos.data.source.local.entities.DarsEntity
import com.aabdelaal.droos.data.source.local.entities.SubjectEntity
import com.aabdelaal.droos.data.source.local.entities.TeacherInfoEntity
import com.aabdelaal.droos.data.source.local.mapping.DroosAndSubject
import com.aabdelaal.droos.data.source.local.mapping.TeacherInfoAndSubject

/**
 * Data Access Object for the droos tables.
 */
@Dao
interface DroosDao {
    /**
     * @return all teachers.
     */
    @Query("SELECT * FROM TeacherInfo")
    fun getTeachers(): LiveData<List<TeacherInfoEntity>>

    /**
     * @return all teachers.
     */
    @Query("SELECT * FROM TeacherInfo where isActive=:isActive")
    fun getTeachersByStatus(isActive: Boolean): LiveData<List<TeacherInfoEntity>>


    /**
     * @param teacherId the id of the Teacher Info
     * @return the teacherInfo object with the id
     */
    @Query("SELECT * FROM teacherinfo where id = :teacherId")
    suspend fun getTecherInfoById(teacherId: Long): TeacherInfoEntity?

    /**
     * Insert a teacher info in the database. If the teacher already exists, replace it.
     *
     * @param teacherInfo the teacherInfo to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTeacherInfo(teacherInfo: TeacherInfoEntity): Long

    /**
     * Delete all teachers.
     */
    @Query("DELETE FROM TeacherInfo")
    suspend fun deleteAllTeacherInfo()

    /**
     * Delete teacher by ID.
     */
    @Query("DELETE FROM TeacherInfo where id=:teacherId")
    suspend fun deleteTeacherInfo(teacherId: Long)


    /**
     * @return all teachers.
     */
    @Query("SELECT * FROM Subject")
    fun getSubjects(): LiveData<List<SubjectEntity>>

    /**
     * @return all subjects with their current teachers(if any).
     */
    @Transaction
    @Query("SELECT * FROM Subject")
    fun getSubjectsWithTeachersInfo(): LiveData<List<TeacherInfoAndSubject>>

    /**
     * Insert a subject in the database. If the subject already exists, replace it.
     *
     * @param subject the subject to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSubject(subject: SubjectEntity): Long


    /**
     * Delete all teachers.
     */
    @Query("DELETE FROM Subject")
    suspend fun deleteAllSubjects()

    /**
     * Delete teacher by ID.
     */
    @Query("DELETE FROM Subject where id=:subjectId")
    suspend fun deleteSubject(subjectId: Long)

    /**
     * @param id the id of the Teacher Info
     * @return the teacherInfo object with the id
     */
    @Transaction
    @Query("SELECT * FROM subject where id = :id")
    suspend fun getSubjectById(id: Long): TeacherInfoAndSubject?

    /**
     * @return all teachers.
     */
    @Query("SELECT * FROM Dars")
    fun getDroos(): LiveData<List<DarsEntity>>

    /**
     * @return all droos with their current darss(if any).
     */
    @Transaction
    @Query("SELECT * FROM Dars")
    fun getDroosWithSubject(): LiveData<List<DroosAndSubject>>

    /**
     * Insert a dars in the database. If the dars already exists, replace it.
     *
     * @param dars the dars to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDars(dars: DarsEntity): Long


    /**
     * Delete all teachers.
     */
    @Query("DELETE FROM Dars")
    suspend fun deleteAllDroos()

    /**
     * Delete teacher by ID.
     */
    @Query("DELETE FROM Dars where id=:darsId")
    suspend fun deleteDars(darsId: Long)

    /**
     * @param id the id of the Teacher Info
     * @return the teacherInfo object with the id
     */
    @Transaction
    @Query("SELECT * FROM dars where id = :id")
    suspend fun getDarsById(id: Long): DroosAndSubject?

}