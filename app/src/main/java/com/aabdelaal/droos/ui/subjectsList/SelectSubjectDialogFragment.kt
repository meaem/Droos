package com.aabdelaal.droos.ui.subjectsList

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.aabdelaal.droos.R
import com.aabdelaal.droos.data.model.Subject
import com.aabdelaal.droos.databinding.FragmentSubjectListBinding


@SuppressLint("LongLogTag")
class SelectSubjectDialogFragment(
    val ok_Action: (t: Subject) -> Unit = {},
    val subjectList: Array<Subject>
) :
    DialogFragment() {
    lateinit var databinding: FragmentSubjectListBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            var selectedItem = -1 // Where we track the selected items
            val builder = AlertDialog.Builder(it)
            val arr = subjectList.map { it.name }.toTypedArray()
//            Log.d(TAG, "onCreateDialog: ${arr?.size}")
            if (subjectList.isEmpty()) {
                builder.setTitle(getString(R.string.no_subject_in_the_list))
                    .setPositiveButton(R.string.btn_ok) { _, id -> }
            } else {
                // Set the dialog title
                builder.setTitle(
                    getString(
                        R.string.manage_subject_form_title,
                        getString(R.string.select)
                    )
                )
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected

                    .setSingleChoiceItems(arr, 0) { _, which ->
                        selectedItem = which
                    }
                    // Set the action buttons
                    .setPositiveButton(R.string.btn_ok) { _, id ->
                        // User clicked OK, so save the selectedItems results somewhere
                        // or return them to the component that opened the dialog
//                        _viewModel.subjectList.value?.get(selectedItem)
//                            ?.let { it1 -> }
                        ok_Action(subjectList.get(selectedItem))
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
        const val TAG = "SelectSubjectDlgF"
    }
}