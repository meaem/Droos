package com.aabdelaal.droos.ui.teacherList

//import com.aabdelaal.droos.data.Result
import android.app.Application

import com.aabdelaal.droos.ui.base.BaseViewModel
import com.aabdelaal.droos.ui.base.NavigationCommand

class ManageTeacherViewModel(val app: Application) : BaseViewModel(app) {
    companion object {
        const val TAG = "TeacherListViewModel"
    }

    fun navigateToAddFragment() {
        navigationCommand.value =
            NavigationCommand.To(TeacherListFragmentDirections.toManageTeacherFragment())
    }


}