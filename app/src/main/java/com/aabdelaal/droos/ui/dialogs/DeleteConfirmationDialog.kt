package com.aabdelaal.droos.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.aabdelaal.droos.R

class DeleteConfirmationDialog(val ok_Action: () -> Unit = {}) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.delete_confirmation))
            .setPositiveButton(getString(R.string.btn_ok)) { _, _ -> ok_Action() }
            .setNegativeButton(getString(R.string.btn_cancel)) { _, _ -> }
            .create()

    companion object {
        const val TAG = "DeleteConfirmationDialog"
    }
}