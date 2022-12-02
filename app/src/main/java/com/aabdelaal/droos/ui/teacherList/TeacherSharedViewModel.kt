package com.aabdelaal.droos.ui.teacherList

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.aabdelaal.droos.R
import com.aabdelaal.droos.data.model.TeacherInfo
import com.aabdelaal.droos.data.source.DroosRepository
import com.aabdelaal.droos.ui.base.BaseViewModel
import com.aabdelaal.droos.ui.base.NavigationCommand
import com.aabdelaal.droos.utils.ManageMode
import com.aabdelaal.droos.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TeacherSharedViewModel(val app: Application, val repository: DroosRepository) :
    BaseViewModel(app) {
    companion object {
        const val TAG = "DroosTeacherVM"
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        this.showErrorMessage.value =
            exception.message ?: app.getString(R.string.err_request_failed)
        println(">> $TAG: Exception thrown:")
        exception.printStackTrace()
        Log.d(TAG, "Exception thrown: $exception.")
    }


    val manageForMode = SingleLiveEvent<ManageMode?>()
    val deleteTeacherEvent = SingleLiveEvent<Boolean>()

    val title = MutableLiveData("Title")
    val actionButtonText = MutableLiveData(app.getString(R.string.btn_save))
    val cancelButtonText = MutableLiveData(app.getString(R.string.btn_cancel))
    val actionButtonVisibility = MutableLiveData(View.VISIBLE)
    val displayOnly = MutableLiveData(false)
    var dialogMode = false;
    private val _currenTeacher = MutableLiveData(TeacherInfo())

    val currentTeacher: LiveData<TeacherInfo>
        get() = _currenTeacher


    private val _teacherList = repository.getTeachers().getOrDefault(MutableLiveData())

    val teacherList: LiveData<List<TeacherInfo>>
        get() = _teacherList
//            _teacherList.map {
//            it.map {
//                TeacherInfo(it.name, it.phone, it.active, it.remoteID, it.id)
//            }
//        }

    val deleteAllVisibility = _teacherList.map {
        when {
            dialogMode -> View.GONE
//            else -> {
            it.isNotEmpty() -> View.VISIBLE
            else -> View.GONE
//            }
        }
    }

    fun setCurrentTeacher(current: TeacherInfo) {
        Log.d(TAG, "setCurrentTeacher")
        _currenTeacher.value = current
    }

    fun validate(): Result<String> {
        if (_currenTeacher.value?.name.isNullOrBlank())
            return Result.failure(Exception(app.getString(R.string.err_name_is_empty)))

        if (_currenTeacher.value?.phone.isNullOrBlank())
            return Result.failure(Exception(app.getString(R.string.err_phone_is_empty)))

        return Result.success(app.getString(R.string.data_is_valid))


    }

    fun doAction() {
        if (displayOnly.value == true) {
            deleteTeacherEvent.value = true
        } else {
            upsertTeacher()
        }

    }

    fun upsertTeacher() {
        showLoading.value = true
        val result = validate()
        Log.d(TAG, "test1")

        if (result.isSuccess) {
            viewModelScope.launch(exceptionHandler) {
                Log.d(TAG, "test2e")
                _currenTeacher.value?.let {
                    repository.saveTeacherInfo(it)

                    withContext(Dispatchers.Main) {
                        showLoading.value = false
                        navigationCommand.value = NavigationCommand.Back
                    }
                }

            }

        } else {
            Log.e(TAG, result.toString())
            println(result.toString())
            showToast.value =
                result.exceptionOrNull()?.message ?: app.getString(R.string.err_cant_add_teacher)
        }
    }

    fun cancelAdd() {
        showLoading.value = false
        _currenTeacher.value = TeacherInfo()
        goBack()
    }

    fun deleteAll() {
        showLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            repository.deleteAllTeachers()
            withContext(Dispatchers.Main) {
                showLoading.value = false
            }
        }

    }

    fun deleteTeacher() {
        currentTeacher.value?.id?.let {
            showLoading.value = true
            viewModelScope.launch(exceptionHandler) {
                repository.deleteTeacherInfo(it)
                withContext(Dispatchers.Main) {
                    showLoading.value = false
                    showToast.value = app.getString(R.string.msg_teacher_deleted)
                    goBack()
                }
            }
        }
    }

    fun navigateToAddFragment() {
        displayOnly.value = false
        _currenTeacher.value = TeacherInfo()
        actionButtonText.value = app.getString(R.string.btn_save)
        actionButtonVisibility.value = View.VISIBLE
        cancelButtonText.value = app.getString(R.string.btn_cancel)

        title.value = app.getString(R.string.manage_teacher_form_title, app.getString(R.string.add))
        manageForMode.value = ManageMode.ADD
        displayOnly.value = false
    }

    fun navigateToEditFragment() {
        Log.d(TAG, "navigateToEditFragment")
        displayOnly.value = false
        title.value =
            app.getString(
                R.string.manage_teacher_form_title,
                app.getString(R.string.update)
            )
        actionButtonText.value = app.getString(R.string.btn_update)
        cancelButtonText.value = app.getString(R.string.btn_cancel)
        actionButtonVisibility.value = View.VISIBLE
        manageForMode.value = ManageMode.UPDATE
        displayOnly.value = false

    }

    fun navigateToDisplayFragment() {

        title.value =
            app.getString(R.string.manage_teacher_form_title, app.getString(R.string.display))
        cancelButtonText.value = app.getString(R.string.btn_ok)
        actionButtonText.value = app.getString(R.string.btn_delete)
        manageForMode.value = ManageMode.DISPLAY
        displayOnly.value = true
    }
}