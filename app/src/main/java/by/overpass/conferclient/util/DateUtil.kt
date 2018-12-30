package by.overpass.conferclient.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun formatPostDate(date: Date?): String {
    val today = SimpleDateFormat("HH:mm", Locale.getDefault())
    val notToday = SimpleDateFormat("dd/MM", Locale.getDefault())
    return if (date != null) {
        if (!date.before(Date(Date().time - TimeUnit.DAYS.toMillis(1)))) {
            today.format(date)
        } else {
            notToday.format(date)
        }
    } else {
        "00:00"
    }
}