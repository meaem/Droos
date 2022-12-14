package com.aabdelaal.droos.ui.teacherList

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.aabdelaal.droos.R
import com.aabdelaal.droos.data.model.TeacherInfo
import com.aabdelaal.droos.databinding.FragmentTeacherListBinding


@SuppressLint("LongLogTag")
class SelectTeacherDialogFragment(
    val ok_Action: (t: TeacherInfo) -> Unit = {},
    val teacherList: Array<TeacherInfo>,
    val selectedTeacher: Int
) :
    DialogFragment() {
    lateinit var databinding: FragmentTeacherListBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            var selectedItem = selectedTeacher // Where we track the selected items
            val builder = AlertDialog.Builder(it)
            val arr = teacherList.map { it.name }.toTypedArray()
//            Log.d(TAG, "onCreateDialog: ${arr?.size}")
            if (teacherList.isEmpty()) {
                builder.setTitle(getString(R.string.no_teacher_in_the_list))
                    .setPositiveButton(R.string.btn_ok) { _, id -> }
            } else {
                // Set the dialog title
                builder.setTitle(
                    getString(
                        R.string.manage_teacher_form_title,
                        getString(R.string.select)
                    )
                )
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected

                    .setSingleChoiceItems(arr, selectedTeacher) { _, which ->
                        selectedItem = which
                    }
                    // Set the action buttons
                    .setPositiveButton(R.string.btn_ok) { _, id ->
                        // User clicked OK, so save the selectedItems results somewhere
                        // or return them to the component that opened the dialog
//                        _viewModel.teacherList.value?.get(selectedItem)
//                            ?.let { it1 -> }
                        ok_Action(teacherList.get(selectedItem))
                    }
                    .setNegativeButton(R.string.btn_cancel) { _, id -> }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
//        AlertDialog.Builder(requireContext())
//            .setMessage(getString(R.string.delete_confirmation))
//            .setPositiveButton(getString(R.string.btn_ok)) { _, _ -> ok_Action() }
//            .setNegativeButton(getString(R.string.btn_cancel)) { _, _ -> }
//            .create()

    companion object {
        const val TAG = "SelectTeacherDlgF"
    }
}