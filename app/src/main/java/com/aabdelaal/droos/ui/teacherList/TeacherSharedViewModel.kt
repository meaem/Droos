package com.aabdelaal.droos.ui.teacherList

//import com.aabdelaal.droos.data.Result
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.aabdelaal.droos.R
import com.aabdelaal.droos.data.source.DroosRepository
import com.aabdelaal.droos.ui.base.BaseViewModel
import com.aabdelaal.droos.ui.base.NavigationCommand
import com.aabdelaal.droos.utils.ManageMode
import com.aabdelaal.droos.utils.SingleLiveEvent
import com.aabdelaal.droos.utils.toDTO
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
        println(">> $TAG: Exception thrown: $exception.")
        Log.d(TAG, "Exception thrown: $exception.")
    }


    val manageForMode = SingleLiveEvent<ManageMode?>()


//    val name = MutableLiveData<String>()
//    val phone = MutableLiveData<String>()
//    val active = MutableLiveData<Boolean>()


    val title = MutableLiveData("Title")
    val actionButtonText = MutableLiveData(app.getString(R.string.btn_save))
    val cancelButtonText = MutableLiveData(app.getString(R.string.btn_cancel))
    val actionButtonVisibility = MutableLiveData(View.VISIBLE)
    val displayOnly = MutableLiveData(false)

    private val _currenTeacher = MutableLiveData(TeacherInfoDataItem())

    val currentTeacher: LiveData<TeacherInfoDataItem>
        get() = _currenTeacher


    private val _teacherList = repository.getTeachers().getOrDefault(MutableLiveData())

    val teacherList: LiveData<List<TeacherInfoDataItem>>
        get() = _teacherList.map {
            it.map {
                TeacherInfoDataItem(it.name, it.phone, it.isActive, it.remoteID, it.id)
            }
        }

    fun setCurrentTeacher(current: TeacherInfoDataItem) {
        Log.d(TAG, "setCurrentTeacher")
        _currenTeacher.value = current
    }

    fun validate(): Result<String> {
//
//        if (_currenTeacher.value == null)
//            return Result.failure(Exception(app.getString(R.string.err_name_is_empty)))
//        if (name.value.isNullOrEmpty())
        if (_currenTeacher.value?.name.isNullOrBlank())
            return Result.failure(Exception(app.getString(R.string.err_name_is_empty)))

        if (_currenTeacher.value?.phone.isNullOrBlank())
            return Result.failure(Exception(app.getString(R.string.err_phone_is_empty)))

        return Result.success(app.getString(R.string.data_is_valid))


    }

    fun doAction() {
        if (displayOnly.value == true) {
            currentTeacher.value?.id?.let { deleteTeacher(it) }
        } else {
            upsertTeacher()
        }

    }

    fun upsertTeacher() {
        showLoading.value = true
        val result = validate()
        if (result.isSuccess) {
            viewModelScope.launch(exceptionHandler) {
                _currenTeacher.value.toDTO()?.let {
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
            showToast.value = app.getString(R.string.err_cant_add_teacher)
        }
    }

    fun cancelAdd() {
        _currenTeacher.value = TeacherInfoDataItem()
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

    fun deleteTeacher(id: Int) {
        showLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            repository.deleteTeacherInfo(id)
            withContext(Dispatchers.Main) {
                showLoading.value = false
                showToast.value = app.getString(R.string.msg_teacher_deleted)
                goBack()
            }
        }

    }

    fun navigateToAddFragment() {
        displayOnly.value = false
        _currenTeacher.value = TeacherInfoDataItem()
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
//        currentTeacher.value?.let {
        manageForMode.value = ManageMode.UPDATE
        displayOnly.value = false
//        }
    }

    fun navigateToDisplayFragment() {

        title.value =
            app.getString(R.string.manage_teacher_form_title, app.getString(R.string.display))
        cancelButtonText.value = app.getString(R.string.btn_ok)
        actionButtonText.value = app.getString(R.string.btn_delete)
//        currentTeacher.value?.let {
        manageForMode.value = ManageMode.DISPLAY
//        }
        displayOnly.value = true
    }
}