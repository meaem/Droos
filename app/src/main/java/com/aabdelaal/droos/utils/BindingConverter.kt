@file:JvmName("Converter")

package com.aabdelaal.droos.utils

import androidx.databinding.InverseMethod
import java.text.SimpleDateFormat
import java.util.*

//class BindingConverter {
private val dateFormatter = SimpleDateFormat("dd/MM/yyyy hh:mm")

@InverseMethod("stringToDate")
fun dateToString(value: Date): String {
    return dateFormatter.format(value)
}


fun stringToDate(value: String): Date {
    return dateFormatter.parse(value)
}


@InverseMethod("stringToInt")
fun intToString(value: Int): String {
    return value.toString()
}


fun stringToInt(value: String): Int {
    return if (value.isNotBlank()) {
        value.toInt()

    } else 0
}
//}