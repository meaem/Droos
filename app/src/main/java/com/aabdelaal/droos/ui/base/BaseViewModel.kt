package com.aabdelaal.droos.ui.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aabdelaal.droos.utils.SingleLiveEvent


/**
 * Base class for View Models to declare the common LiveData objects in one place
 */
abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    private companion object {
        const val TAG = "DroosBaseViewModel"
    }

    val navigationCommand: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val showErrorMessage: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBar: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBarInt: SingleLiveEvent<Int> = SingleLiveEvent()
    val showToast: SingleLiveEvent<String> = SingleLiveEvent()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()


    fun goBack() {
        Log.d(TAG, "going back")
        navigationCommand.value = NavigationCommand.Back
    }

}