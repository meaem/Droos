package com.aabdelaal.droos.ui.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DateTimeSelectDialog(val ok_Action: (hourOfDay: Int, minute: Int, year: Int, month: Int, dayOfMonth: Int) -> Unit = { _, _, _, _, _ -> }) :
    DialogFragment() {

    companion object {
        const val TAG = "DeleteConfirmationDialog"
    }

    // Use the current time as the default values for the picker
    private val c = Calendar.getInstance()
    private var _hour = c.get(Calendar.HOUR_OF_DAY)
    private var _minute = c.get(Calendar.MINUTE)
    private var _year = c.get(Calendar.YEAR)
    private var _month = c.get(Calendar.MONTH)
    private var _dayOfMonth = c.get(Calendar.DAY_OF_MONTH)


    private val onSetDate = fun(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        ok_Action(_hour, _minute, year, month, dayOfMonth)

    }
    private val onSetTime = fun(_: TimePicker, hourOfDay: Int, minute: Int) {
        _hour = hourOfDay
        _minute = minute
        DatePickerDialog(requireContext(), onSetDate, _year, _month, _dayOfMonth).show()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =

        TimePickerDialog(
            requireContext(),
            onSetTime,
            _hour,
            _minute,
            DateFormat.is24HourFormat(activity)
        )


}