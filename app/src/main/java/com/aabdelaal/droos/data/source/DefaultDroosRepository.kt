/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aabdelaal.droos.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import com.aabdelaal.droos.data.model.Dars
import com.aabdelaal.droos.data.model.Subject
import com.aabdelaal.droos.data.model.TeacherInfo
import com.aabdelaal.droos.data.source.local.LocalDataSource
import com.aabdelaal.droos.data.source.remote.RemoteDataSource
import com.aabdelaal.droos.utils.asRemote
import com.aabdelaal.droos.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 */
class DefaultDroosRepository(
    private val droosRemoteDataSource: RemoteDataSource,
    private val droosLocalDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DroosRepository {
    private companion object {
        const val TAG = "DroosDefDroosRepo"
    }




    //    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
//        wrapEspressoIdlingResource {
//            if (forceUpdate) {
//                try {
//                    updateTasksFromRemoteDataSource()
//                } catch (ex: Exception) {
//                    return Result.Error(ex)
//                }
//            }
//            return tasksLocalDataSource.getTasks()
//        }
//    }
//
//    override suspend fun refreshTasks() {
//        wrapEspressoIdlingResource {
//            updateTasksFromRemoteDataSource()
//        }
//    }
//
//    override fun observeTasks(): LiveData<Result<List<Task>>> {
//        wrapEspressoIdlingResource {
//            return tasksLocalDataSource.observeTasks()
//        }
//    }
//
//    override suspend fun refreshTask(taskId: String) {
//        wrapEspressoIdlingResource {
//            updateTaskFromRemoteDataSource(taskId)
//        }
//    }
//
//    private suspend fun updateTasksFromRemoteDataSource() {
//        wrapEspressoIdlingResource {
//
//            val remoteTasks = tasksRemoteDataSource.getTasks()
//
//            if (remoteTasks is Success) {
//                // Real apps might want to do a proper sync.
//                tasksLocalDataSource.deleteAllTasks()
//                remoteTasks.data.forEach { task ->
//                    tasksLocalDataSource.saveTask(task)
//                }
//            } else if (remoteTasks is Result.Error) {
//                throw remoteTasks.exception
//            }
//        }
//    }
//
//    override fun observeTask(taskId: String): LiveData<Result<Task>> {
//        wrapEspressoIdlingResource {
//            return tasksLocalDataSource.observeTask(taskId)
//        }
//    }
//
//    private suspend fun updateTaskFromRemoteDataSource(taskId: String) {
//        wrapEspressoIdlingResource {
//            val remoteTask = tasksRemoteDataSource.getTask(taskId)
//
//            if (remoteTask is Success) {
//                tasksLocalDataSource.saveTask(remoteTask.data)
//            }
//        }
//    }
//
//    /**
//     * Relies on [getTasks] to fetch data and picks the task with the same ID.
//     */
//    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
//        wrapEspressoIdlingResource {
//            if (forceUpdate) {
//                updateTaskFromRemoteDataSource(taskId)
//            }
//            return tasksLocalDataSource.getTask(taskId)
//        }
//    }
//
//    override suspend fun saveTask(task: Task) {
//        wrapEspressoIdlingResource {
//            coroutineScope {
//                launch { tasksRemoteDataSource.saveTask(task) }
//                launch { tasksLocalDataSource.saveTask(task) }
//            }
//        }
//    }
//
//    override suspend fun completeTask(task: Task) {
//        wrapEspressoIdlingResource {
//            coroutineScope {
//                launch { tasksRemoteDataSource.completeTask(task) }
//                launch { tasksLocalDataSource.completeTask(task) }
//            }
//        }
//    }
//
//    override suspend fun completeTask(taskId: String) {
//        wrapEspressoIdlingResource {
//            withContext(ioDispatcher) {
//                (getTaskWithId(taskId) as? Success)?.let { it ->
//                    completeTask(it.data)
//                }
//            }
//        }
//    }
//
//    override suspend fun activateTask(task: Task) = withContext<Unit>(ioDispatcher) {
//        wrapEspressoIdlingResource {
//            coroutineScope {
//                launch { tasksRemoteDataSource.activateTask(task) }
//                launch { tasksLocalDataSource.activateTask(task) }
//            }
//        }
//    }
//
//    override suspend fun activateTask(taskId: String) {
//        wrapEspressoIdlingResource {
//            withContext(ioDispatcher) {
//                (getTaskWithId(taskId) as? Success)?.let { it ->
//                    activateTask(it.data)
//                }
//            }
//        }
//    }
//
//    override suspend fun clearCompletedTasks() {
//        wrapEspressoIdlingResource {
//            coroutineScope {
//                launch { tasksRemoteDataSource.clearCompletedTasks() }
//                launch { tasksLocalDataSource.clearCompletedTasks() }
//            }
//        }
//    }
//
//    override suspend fun deleteAllTasks() {
//        wrapEspressoIdlingResource {
//            withContext(ioDispatcher) {
//                coroutineScope {
//                    launch { tasksRemoteDataSource.deleteAllTasks() }
//                    launch { tasksLocalDataSource.deleteAllTasks() }
//                }
//            }
//        }
//    }
//
//    override suspend fun deleteTask(taskId: String) {
//        wrapEspressoIdlingResource {
//            coroutineScope {
//                launch { tasksRemoteDataSource.deleteTask(taskId) }
//                launch { tasksLocalDataSource.deleteTask(taskId) }
//            }
//        }
//    }
//
//    private suspend fun getTaskWithId(id: String): Result<Task> {
//        wrapEspressoIdlingResource {
//            return tasksLocalDataSource.getTask(id)
//        }
//    }


    override suspend fun saveTeacherInfo(teacherInfo: TeacherInfo) {
        wrapEspressoIdlingResource {
            coroutineScope {
//                try {
                var remoteId: String?
                withContext(ioDispatcher) {
                    Log.d(TAG, "b4 add remotely")
                    if (teacherInfo.remoteID == null) {
                        remoteId = droosRemoteDataSource.addTeacherInfo(teacherInfo.asRemote())
                        Log.d(TAG, "after add remotely. remoteId:$remoteId")
                        teacherInfo.remoteID = remoteId
                        println("after add remotely")
                    } else {
                        droosRemoteDataSource.updateTeacherInfo(teacherInfo.asRemote())
                    }

                }

                withContext(ioDispatcher) {
                    Log.d(TAG, "b4 add locally")
                    droosLocalDataSource.saveTeacherInfo(teacherInfo)
                    Log.d(TAG, "after add locally")
                }

//                    launch { }
//                } catch (ex: Exception) {
//                    println( ex.message.toString())
//                }
            }
        }
    }

    override suspend fun getTeacherInfoById(id: Long): Result<TeacherInfo> {
        wrapEspressoIdlingResource {
            TODO("Not yet implemented")
        }
    }

    override fun getTeachers(): Result<LiveData<List<TeacherInfo>>> {
        wrapEspressoIdlingResource {
            return droosLocalDataSource.getTeachers()
//            if (result.isFailure) return Result.failure(result.exceptionOrNull()!!)
//            else{
//                return  Result.success(result.getOrNull()!!.map {
//                    it.map {
//                        it.asExternalModel()
//                    }
//                })
//            }
        }
    }

    override fun getTeachersByStatus(isActive: Boolean): Result<LiveData<List<TeacherInfo>>> {
        wrapEspressoIdlingResource {
            return droosLocalDataSource.getTeachersByStatus(isActive)
//            if (result.isFailure) return Result.failure(result.exceptionOrNull()!!)
//            else{
//                return  Result.success(result.getOrNull()!!.map {
//                    it.map {
//                        it.asExternalModel()
//                    }
//                })
//            }
        }
    }

    override suspend fun deleteAllTeachers() {
        wrapEspressoIdlingResource {
            droosRemoteDataSource.deleteAllTeachers()
            droosLocalDataSource.deleteAllTeachers()
        }
    }

    override suspend fun deleteTeacherInfo(id: Long) {
        wrapEspressoIdlingResource {
            val result = droosLocalDataSource.getTeacherInfoById(id)
            if (result.isSuccess) {
                result.getOrNull()?.remoteID?.let { droosRemoteDataSource.deleteTeacherInfo(it) }
                droosLocalDataSource.deleteTeacherInfo(id)
            }
        }

    }

    override fun getSubjects(): Result<LiveData<List<Subject>>> {
        wrapEspressoIdlingResource {

            return droosLocalDataSource.getSubjects()
        }
    }

    override suspend fun saveSubject(subject: Subject) {
        wrapEspressoIdlingResource {
            coroutineScope {
//                try {
                var remoteId: String?
                withContext(ioDispatcher) {
                    Log.d(TAG, "b4 add remotely")
                    if (subject.remoteID == null) {
                        remoteId = droosRemoteDataSource.addSubject(subject.asRemote())
                        Log.d(TAG, "after add remotely. remoteId:$remoteId")
                        subject.remoteID = remoteId
                        println("after add remotely")
                    } else {
                        droosRemoteDataSource.updateSubject(subject.asRemote())
                    }
                }

                withContext(ioDispatcher) {
                    Log.d(TAG, "b4 add locally")
                    droosLocalDataSource.saveSubject(subject)
                    Log.d(TAG, "after add locally")
                }

//                    launch { }
//                } catch (ex: Exception) {
//                    println( ex.message.toString())
//                }
            }
        }
    }

    override suspend fun getSubjectById(id: Long): Result<Subject> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllSubjects() {
        wrapEspressoIdlingResource {
            droosRemoteDataSource.deleteAllSubjects()
            droosLocalDataSource.deleteAllSubjects()
        }
    }

    override suspend fun deleteSubject(id: Long) {
        wrapEspressoIdlingResource {
            val result = droosLocalDataSource.getSubjectById(id)
            if (result.isSuccess) {
                result.getOrNull()?.remoteID?.let { droosRemoteDataSource.deleteSubject(it) }
                droosLocalDataSource.deleteSubject(id)
            }
        }
    }

    override fun getDroos(): Result<LiveData<List<Dars>>> {
        wrapEspressoIdlingResource {

            return droosLocalDataSource.getDroos()
        }
    }

    override suspend fun saveDars(dars: Dars) {
        wrapEspressoIdlingResource {
            coroutineScope {
//                try {
                var remoteId: String?
                withContext(ioDispatcher) {
                    Log.d(TAG, "b4 add remotely")
                    if (dars.remoteID == null) {
                        remoteId = droosRemoteDataSource.addDars(dars.asRemote())
                        Log.d(TAG, "after add remotely. remoteId:$remoteId")
                        dars.remoteID = remoteId
                        println("after add remotely")
                    } else {
                        droosRemoteDataSource.updateDars(dars.asRemote())
                    }
                }

                withContext(ioDispatcher) {
                    Log.d(TAG, "b4 add locally")
                    droosLocalDataSource.saveDars(dars)
                    Log.d(TAG, "after add locally")
                }

//                    launch { }
//                } catch (ex: Exception) {
//                    println( ex.message.toString())
//                }
            }
        }
    }

    override suspend fun getDarsById(id: Long): Result<Dars> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllDroos() {
        wrapEspressoIdlingResource {
            droosRemoteDataSource.deleteAllDroos()
            droosLocalDataSource.deleteAllDroos()
        }
    }

    override suspend fun deleteDars(id: Long) {
        wrapEspressoIdlingResource {
            val result = droosLocalDataSource.getDarsById(id)
            if (result.isSuccess) {
                result.getOrNull()?.remoteID?.let { droosRemoteDataSource.deleteDars(it) }
                droosLocalDataSource.deleteDars(id)
            }
        }
    }


}
