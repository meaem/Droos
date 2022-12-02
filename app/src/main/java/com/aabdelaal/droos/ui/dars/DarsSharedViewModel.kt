package com.aabdelaal.droos.ui.dars

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.aabdelaal.droos.R
import com.aabdelaal.droos.data.model.Dars
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
import java.util.*


class DarsSharedViewModel(val app: Application, val repository: DroosRepository) :
    BaseViewModel(app) {
    companion object {
        const val TAG = "DroosDarsVM"
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        this.showErrorMessage.value =
            exception.message ?: app.getString(R.string.err_request_failed)
        println(">> $TAG: Exception thrown:")
        exception.printStackTrace()
        Log.d(TAG, "Exception thrown: $exception.")
    }


    val manageForMode = SingleLiveEvent<ManageMode?>()
    val selectSubjectEvent = SingleLiveEvent<Boolean>()
    val selectDateTimeEvent = SingleLiveEvent<Boolean>()
    val deleteDarsEvent = SingleLiveEvent<Boolean>()

    val title = MutableLiveData("Title")
    val actionButtonText = MutableLiveData(app.getString(R.string.btn_save))
    val cancelButtonText = MutableLiveData(app.getString(R.string.btn_cancel))
    val actionButtonVisibility = MutableLiveData(View.VISIBLE)
    val displayOnly = MutableLiveData(false)

    var dialogIsReady = false

    private val _currenDars = MutableLiveData(Dars())

    val currentDars: LiveData<Dars>
        get() = _currenDars

    private val _darsList = repository.getDroos().getOrDefault(MutableLiveData())

    val darsList: LiveData<List<Dars>>
        get() = _darsList


    private val _subjectList = repository.getSubjects().getOrDefault(MutableLiveData())

    val subjectList: LiveData<List<Subject>>
        get() = _subjectList


//            _darsList.map {
//            it.map {
//                Dars(it.name, null, it.isActive, it.remoteID, it.id)
//            }
//        }

    val deleteAllVisibility = _darsList.map {
        when (it.isNotEmpty()) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    fun setCurrentDars(current: Dars) {
        Log.d(TAG, "setCurrentDars")
        _currenDars.value = current
    }

    fun validate(): Result<String> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 1)
        val tomorrow = cal.time

        if (_currenDars.value?.subject == null)
            return Result.failure(Exception(app.getString(R.string.err_subject_is_empty)))

        if (_currenDars.value?.date?.after(tomorrow) == true)
            return Result.failure(Exception(app.getString(R.string.err_date_in_future)))

        if (_currenDars.value?.duration == 0 || _currenDars.value?.duration!! > 3)
            return Result.failure(Exception(app.getString(R.string.err_duration_not_correct)))


//        if (_currenDars.value?.teacher == null)
//            return Result.failure(Exception(app.getString(R.string.err_teacher_is_empty)))

        return Result.success(app.getString(R.string.data_is_valid))


    }

    fun doAction() {
        if (displayOnly.value == true) {
            deleteDarsEvent.value = true

        } else {
            upsertDars()
        }
    }

    fun upsertDars() {
        showLoading.value = true
        val result = validate()
        if (result.isSuccess) {
            viewModelScope.launch(exceptionHandler) {
                _currenDars.value.let {
                    repository.saveDars(it!!)

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
                result.exceptionOrNull()?.message ?: app.getString(R.string.err_cant_add_dars)
        }
    }

    fun cancelAdd() {
        showLoading.value = false
        _currenDars.value = Dars()
        goBack()
    }

    fun deleteAll() {

        showLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            repository.deleteAllDroos()
            withContext(Dispatchers.Main) {
                showLoading.value = false
            }
        }

    }

    fun deleteDars() {
        currentDars.value?.id?.let {
            showLoading.value = true
            viewModelScope.launch(exceptionHandler) {
                repository.deleteDars(it)
                withContext(Dispatchers.Main) {
                    showLoading.value = false
                    showToast.value = app.getString(R.string.msg_dars_deleted)
                    goBack()
                }
            }
        }
    }

    fun navigateToAddFragment() {
        displayOnly.value = false
        _currenDars.value = Dars()
        actionButtonText.value = app.getString(R.string.btn_save)
        actionButtonVisibility.value = View.VISIBLE
        cancelButtonText.value = app.getString(R.string.btn_cancel)

        title.value = app.getString(R.string.manage_dars_form_title, app.getString(R.string.add))
        manageForMode.value = ManageMode.ADD
        displayOnly.value = false
    }

    fun navigateToEditFragment() {
        Log.d(TAG, "navigateToEditFragment")
        displayOnly.value = false
        title.value =
            app.getString(
                R.string.manage_dars_form_title,
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
            app.getString(R.string.manage_dars_form_title, app.getString(R.string.display))
        cancelButtonText.value = app.getString(R.string.btn_ok)
        actionButtonText.value = app.getString(R.string.btn_delete)
        manageForMode.value = ManageMode.DISPLAY
        displayOnly.value = true
    }

    fun navigateToSelectFragment() {

//        title.value =
//            app.getString(R.string.manage_dars_form_title, app.getString(R.string.select))
//        cancelButtonText.value = app.getString(R.string.btn_ok)
//        actionButtonText.value = app.getString(R.string.btn_delete)
        selectSubjectEvent.value = true

    }

    fun selectDate() {
        selectDateTimeEvent.value = true
    }


}