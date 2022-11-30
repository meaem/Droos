package com.aabdelaal.droos.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aabdelaal.droos.data.source.remote.models.RemoteSubject
import com.aabdelaal.droos.data.source.remote.models.RemoteTeacherInfo
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class DroosRemoteDataSource(private val userLD: LiveData<FirebaseUser?>) : RemoteDataSource {

    private companion object {
        const val TAG = "DroosRemoteRepository"
        const val TEACHER_COLLECTION = "teachers"
        const val SUBJECT_COLLECTION = "subjects"
        const val USER_TEACHER_COLLECTION = "user_teachers"
        const val USER_SUBJECT_COLLECTION = "user_subjects"
    }

//    private val db = Firebase.firestore

    private val teachersCollectionRef by lazy {
//        Log.d(TAG, "current user live data :" + userLD.value.toString())
        userLD.value?.let {
            Firebase.firestore.collection(TEACHER_COLLECTION).document(it.uid)
                .collection(USER_TEACHER_COLLECTION)
        }!!
    }
    private val subjectsCollectionRef by lazy {
//        Log.d(TAG, "current user live data :" + userLD.value.toString())
        userLD.value?.let {
            Firebase.firestore.collection(SUBJECT_COLLECTION).document(it.uid)
                .collection(USER_SUBJECT_COLLECTION)
        }!!
    }

    private val techersLiveData = MutableLiveData<List<RemoteTeacherInfo>>()
    private val subjectsLiveData = MutableLiveData<List<RemoteSubject>>()


//    private val _teachers = MutableLiveData<MutableList<TeacherInfoDTO>>()

//    val teachers: LiveData<MutableList<TeacherInfoDTO>>
//        get() = _teachers


//    init {
//        teachersCollectionRef.addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//
//            val source = if (snapshot != null && snapshot.metadata.hasPendingWrites())
//                "Local"
//            else
//                "Server"
//
//            if (snapshot != null && snapshot.exists()) {
//                Log.d(TAG, "$source data: ${snapshot.data}")
//                _teachers.value = snapshot.toObjects(TeacherInfoDTO::class.java)
//            } else {
//                Log.d(TAG, "$source data: null")
//                _teachers.value = mutableListOf()
//            }
//        }
//    }


    override suspend fun addTeacherInfo(teacherInfo: RemoteTeacherInfo): String {
        println("before remote add to   ${teachersCollectionRef.path}")
        Log.d(TAG, "before remote add to ${teachersCollectionRef.path}")
        val x = teachersCollectionRef
            .add(teacherInfo)
            .await()
        Log.d(TAG, "after remote add to  ${teachersCollectionRef.path}")
        println("after remote add to  ${teachersCollectionRef.path}")
        return x.id

    }

    override suspend fun updateTeacherInfo(teacherInfo: RemoteTeacherInfo) {
        println("before remote update to ${teachersCollectionRef.path}")
        Log.d(TAG, "before remote update to ${teachersCollectionRef.path}")

        teachersCollectionRef.document(teacherInfo.remoteID!!)
            .set(teacherInfo)
            .await()
        Log.d(TAG, "after remote update to${teachersCollectionRef.path}")
        println("after remote update to ${teachersCollectionRef.path}")
    }

    override suspend fun getTeacherInfoById(id: String): Result<RemoteTeacherInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTeachers() {
        teachersCollectionRef
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    teachersCollectionRef.document(document.id).delete()
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .await()
    }

    override suspend fun deleteTeacherInfo(id: String) {
        teachersCollectionRef
            .document(id)
            .delete()

            .await()
    }


    override suspend fun getTeachers(): Result<LiveData<List<RemoteTeacherInfo>>> {
        try {
            val reuslt = teachersCollectionRef.get().await()
            val teachers = reuslt.toObjects(RemoteTeacherInfo::class.java)
//        val result = MutableLiveData(teachersCollectionRef.get().await())
            techersLiveData.value = teachers
            return Result.success(techersLiveData)
        } catch (ex: Exception) {
            return Result.failure(ex)
        }

    }

    override fun getTeachersByStatus(isActive: Boolean): Result<LiveData<List<RemoteTeacherInfo>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubjects(): Result<LiveData<List<RemoteSubject>>> {
        try {
            val reuslt = subjectsCollectionRef.get().await()
            val subjects = reuslt.toObjects(RemoteSubject::class.java)
            subjectsLiveData.value = subjects
            return Result.success(subjectsLiveData)
        } catch (ex: Exception) {
            return Result.failure(ex)
        }
    }

    override suspend fun addSubject(subject: RemoteSubject): String {
        println("before remote add to  ${subjectsCollectionRef.path}")
        Log.d(TAG, "before remote add to  ${subjectsCollectionRef.path}")
        val x = subjectsCollectionRef
            .add(subject)
            .await()
        Log.d(TAG, "after remote add to ${subjectsCollectionRef.path}")
        println("after remote add to ${subjectsCollectionRef.path}")
        return x.id
    }

    override suspend fun updateSubject(subject: RemoteSubject) {
        println("before remote update to ${subjectsCollectionRef.path}")
        Log.d(TAG, "before remote update to ${subjectsCollectionRef.path}")

        subjectsCollectionRef.document(subject.remoteID!!)
            .set(subject)
            .await()
        Log.d(TAG, "after remote update to${subjectsCollectionRef.path}")
        println("after remote update to ${subjectsCollectionRef.path}")
    }

    override suspend fun deleteAllSubjects() {
        subjectsCollectionRef
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    subjectsCollectionRef.document(document.id).delete()
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .await()
    }

    override suspend fun deleteSubject(id: String) {
        subjectsCollectionRef
            .document(id)
            .delete()
            .await()
    }

}
