package com.aabdelaal.droos.data.source.local


import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.aabdelaal.droos.data.model.Subject
import com.aabdelaal.droos.data.model.TeacherInfo
import com.aabdelaal.droos.utils.asEntity
import com.aabdelaal.droos.utils.asExternalModel
import com.aabdelaal.droos.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation of a data source as a db.
 *
 * The repository is implemented so that you can focus on only testing it.
 *
 * @param droosDao the dao that does the Room db operations
 * @param ioDispatcher a coroutine dispatcher to offload the blocking IO tasks
 */
class DroosLocalDataSource(
    private val droosDao: DroosDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalDataSource {

    /**
     * Get the teachers list from the local db
     * @return Result the holds a Success with all the teacherInfo or an Error object with the error message
     */
    override fun getTeachers(): Result<LiveData<List<TeacherInfo>>> {
        val result = droosDao.getTeachers()

        return Result.success(result.map {
            it.map {
                it.asExternalModel()
            }
        })
    }


    override fun getTeachersByStatus(isActive: Boolean): Result<LiveData<List<TeacherInfo>>> {
        val result = droosDao.getTeachersByStatus(isActive)
//        if (result.isFailure) return Result.failure(result.exceptionOrNull()!!)
//        else{
        return Result.success(result.map {
            it.map {
                it.asExternalModel()
            }
        })
//        }

    }


    /**
     * Insert a teacher Info in the db.
     * @param teacherInfo the teacher Info to be inserted
     */
    override suspend fun saveTeacherInfo(teacherInfo: TeacherInfo): Long {
        var id: Long = 0
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                id = droosDao.saveTeacherInfo(teacherInfo.asEntity())
            }
        }
        return id
    }
//
    /**
     * Get a teacher Info by its id
     * @param id to be used to get the teacherInfo
     * @return Result the holds a Success object with the teacherInfo or an Error object with the error message
     */
    override suspend fun getTeacherInfoById(id: Long): Result<TeacherInfo> =
        withContext(ioDispatcher) {
            wrapEspressoIdlingResource {
                try {
                    val teacherInfoEntity = droosDao.getTecherInfoById(id)
                    if (teacherInfoEntity != null) {
                        return@withContext Result.success(teacherInfoEntity.asExternalModel())
                    } else {
                        return@withContext Result.failure(Exception("Teacher Info not found!"))
                    }
                } catch (e: Exception) {
                    return@withContext Result.failure(e)
                }
            }
        }

    /**
     * Deletes all the TeacherInfo in the db
     */
    override suspend fun deleteAllTeachers() {
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                droosDao.deleteAllTeacherInfo()
            }
        }
    }

    /**
     * Deletes one the TeacherInfo in the db
     */
    override suspend fun deleteTeacherInfo(id: Long) {
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                droosDao.deleteTeacherInfo(id)
            }
        }
    }

    override fun getSubjects(): Result<LiveData<List<Subject>>> {
        val result = droosDao.getSubjectsWithTeachersInfo()

        return Result.success(result.map {
            it.map {
                it.asExternalModel()
            }
        })
    }

    override suspend fun getSubjectById(id: Long): Result<Subject> =
        withContext(ioDispatcher) {
            wrapEspressoIdlingResource {
                try {
                    val subjectEntity = droosDao.getSubjectById(id)
                    if (subjectEntity != null) {
                        return@withContext Result.success(subjectEntity.asExternalModel())
                    } else {
                        return@withContext Result.failure(Exception("Subject Info not found!"))
                    }
                } catch (e: Exception) {
                    return@withContext Result.failure(e)
                }
            }
        }


    override suspend fun saveSubject(subject: Subject): Long {
        var id: Long = 0
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                id = droosDao.saveSubject(subject.asEntity())
            }
        }
        return id
    }


    /**
     * Deletes one the Subject from the db
     */
    override suspend fun deleteSubject(id: Long) {
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                droosDao.deleteSubject(id)
            }
        }
    }

    /**
     * Deletes one the Subject from the db
     */
    override suspend fun deleteAllSubjects() {
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                droosDao.deleteAllSubjects()
            }
        }
    }

}


