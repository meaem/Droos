package com.aabdelaal.droos.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import com.aabdelaal.droos.data.dto.TeacherInfoDTO
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class DroosRemoteDataSource(private val userLD: LiveData<FirebaseUser?>) : RemoteDataSource {

    private companion object {
        const val TAG = "DroosRemoteRepository"
        const val TEACHER_COLLECTION = "teachers"
        const val USER_TEACHER_COLLECTION = "user_teachers"
    }

//    private val db = Firebase.firestore

    private val teachersCollectionRef by lazy {
        Log.d(TAG, "current user live data :" + userLD.value.toString())
        userLD.value?.let {
            Firebase.firestore.collection(TEACHER_COLLECTION).document(it.uid)
                .collection(USER_TEACHER_COLLECTION)
        }!!
    }


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


    override suspend fun addTeacherInfo(teacherInfo: TeacherInfoDTO): String {
        println("before remote add to  db.collection(\"teachers\")")
        Log.d(TAG, "before remote add to  db.collection(\"teachers\")")
        val x = teachersCollectionRef
            .add(teacherInfo)
            .await()
        Log.d(TAG, "after remote add to  db.collection(\"teachers\")")
        println("after remote add to  db.collection(\"teachers\")")
        return x.id

    }

    override suspend fun updateTeacherInfo(teacherInfo: TeacherInfoDTO) {
        println("before remote update to  db.collection(\"teachers\\${teacherInfo.remoteID}\")")
        Log.d(TAG, "before remote update to  db.collection(\"teachers\\${teacherInfo.remoteID}\")")

        teachersCollectionRef.document(teacherInfo.remoteID!!)
            .set(teacherInfo)
            .await()
        Log.d(TAG, "after remote update to  db.collection(\"teachers\\${teacherInfo.remoteID}\")")
        println("after remote update to  db.collection(\"teachers\\${teacherInfo.remoteID}\")")
    }

    override suspend fun getTeacherInfoById(id: String): Result<TeacherInfoDTO> {
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

    override fun getTeachers(): Result<LiveData<List<TeacherInfoDTO>>> {
        TODO("Not yet implemented")
    }

    override fun getTeachersByStatus(isActive: Boolean): Result<LiveData<List<TeacherInfoDTO>>> {
        TODO("Not yet implemented")
    }
}
