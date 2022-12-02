package com.aabdelaal.droos.ui.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DateTimeSelectDialog(val ok_Action: (hourOfDay: Int, minute: Int) -> Unit = { _, _ -> }) :
    DialogFragment(), TimePickerDialog.OnTimeSetListener {
    // Use the current time as the default values for the picker
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =

        TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))

    companion object {
        const val TAG = "DeleteConfirmationDialog"
    }

    /**
     * Called when the user is done setting a new time and the dialog has
     * closed.
     *
     * @param view the view associated with this listener
     * @param hourOfDay the hour that was set
     * @param minute the minute that was set
     */
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        ok_Action(hourOfDay, minute)
    }
}