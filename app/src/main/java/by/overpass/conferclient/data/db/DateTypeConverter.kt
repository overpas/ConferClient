package by.overpass.conferclient.data.db

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateTypeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
