package com.aabdelaal.droos.data.source.local

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}