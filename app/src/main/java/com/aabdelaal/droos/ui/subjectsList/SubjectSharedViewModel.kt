package com.aabdelaal.droos.ui.subjectsList

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.aabdelaal.droos.R
import com.aabdelaal.droos.data.model.Subject
import com.aabdelaal.droos.data.source.DroosRepository
import com.aabdelaal.droos.ui.base.BaseViewModel
import com.aabdelaal.droos.ui.base.NavigationCommand
import com.aabdelaal.droos.utils.ManageMode
import com.aabdelaal.droos.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SubjectSharedViewModel(val app: Application, val repository: DroosRepository) :
    BaseViewModel(app) {
    companion object {
        const val TAG = "DroosSubjectVM"
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        this.showErrorMessage.value =
            exception.message ?: app.getString(R.string.err_request_failed)
        println(">> $TAG: Exception thrown:")
        exception.printStackTrace()
        Log.d(TAG, "Exception thrown: $exception.")
    }


    val manageForMode = SingleLiveEvent<ManageMode?>()

    val title = MutableLiveData("Title")
    val actionButtonText = MutableLiveData(app.getString(R.string.btn_save))
    val cancelButtonText = MutableLiveData(app.getString(R.string.btn_cancel))
    val actionButtonVisibility = MutableLiveData(View.VISIBLE)
    val displayOnly = MutableLiveData(false)

    private val _currenSubject = MutableLiveData(Subject())

    val currentSubject: LiveData<Subject>
        get() = _currenSubject

    private val _teacherList = repository.getSubjects().getOrDefault(MutableLiveData())

    val subjectList: LiveData<List<SubjectDataItem>>
        get() = _teacherList.map {
            it.map {
                SubjectDataItem(it.name, null, it.isActive, it.remoteID, it.id)
            }
        }

    val deleteAllVisibility = _teacherList.map {
        when (it.isNotEmpty()) {
            true -> View.VISIBLE
            false -> View.GONE
        }

    }

    fun setCurrentSubject(current: Subject) {
        Log.d(TAG, "setCurrentSubject")
        _currenSubject.value = current
    }

    fun validate(): Result<String> {
        if (_currenSubject.value?.name.isNullOrBlank())
            return Result.failure(Exception(app.getString(R.string.err_name_is_empty)))

        if (_currenSubject.value?.teacher == null)
            return Result.failure(Exception(app.getString(R.string.err_teacher_is_empty)))

        return Result.success(app.getString(R.string.data_is_valid))


    }

    fun doAction() {
        if (displayOnly.value == true) {
            currentSubject.value?.id?.let { deleteSubject(it) }
        } else {
            upsertSubject()
        }
    }

    fun upsertSubject() {
        showLoading.value = true
        val result = validate()
        if (result.isSuccess) {
            viewModelScope.launch(exceptionHandler) {
                _currenSubject.value.let {
                    repository.saveSubject(it!!)

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
        _currenSubject.value = Subject()
        goBack()
    }

    fun deleteAll() {
        showLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            repository.deleteAllSubjects()
            withContext(Dispatchers.Main) {
                showLoading.value = false
            }
        }

    }

    fun deleteSubject(id: Int) {
        showLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            repository.deleteSubject(id)
            withContext(Dispatchers.Main) {
                showLoading.value = false
                showToast.value = app.getString(R.string.msg_teacher_deleted)
                goBack()
            }
        }

    }

    fun navigateToAddFragment() {
        displayOnly.value = false
        _currenSubject.value = Subject()
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