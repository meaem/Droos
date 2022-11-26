package com.aabdelaal.droos.data.source.local


import com.aabdelaal.droos.data.dto.TeacherInfoDTO
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
    override fun getTeachers() = Result.success(droosDao.getTeachers())


    override fun getTeachersByStatus(isActive: Boolean) =
        Result.success(droosDao.getTeachersByStatus(isActive))


    /**
     * Insert a teacher Info in the db.
     * @param teacherInfo the teacher Info to be inserted
     */
    override suspend fun saveTeacherInfo(teacherInfo: TeacherInfoDTO): Long {
        var id: Long = 0
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                id = droosDao.saveTeacherInfo(teacherInfo)
            }
        }
        return id;
    }
//
    /**
     * Get a teacher Info by its id
     * @param id to be used to get the teacherInfo
     * @return Result the holds a Success object with the teacherInfo or an Error object with the error message
     */
    override suspend fun getTeacherInfoById(id: Int): Result<TeacherInfoDTO> =
        withContext(ioDispatcher) {
            wrapEspressoIdlingResource {
                try {
                    val teacherInfoDTO = droosDao.getTecherInfoById(id)
                    if (teacherInfoDTO != null) {
                        return@withContext Result.success(teacherInfoDTO)
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
    override suspend fun deleteTeacherInfo(id: Int) {
        wrapEspressoIdlingResource {
            withContext(ioDispatcher) {
                droosDao.deleteTeacherInfo(id)
            }
        }
    }

}


