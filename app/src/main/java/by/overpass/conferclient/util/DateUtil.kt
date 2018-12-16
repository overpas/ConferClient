package by.overpass.conferclient.util

import java.text.SimpleDateFormat
import java.util.*

fun formatPostDate(date: Date?): String {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return if (date != null) {
        format.format(date)
    } else {
        "00:00"
    }
}