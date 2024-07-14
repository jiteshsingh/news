package me.jitesh.android.news

import android.view.View
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Synchronized
fun SimpleDateFormat.synchronizedFormat(any: Any): String {
    return format(any)
}

@Synchronized
fun SimpleDateFormat.synchronizedParse(date: String): Date {
    return parse(date)!!
}

fun Calendar.clearTime(): Calendar {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return this
}

val AlertDialog.positiveButton: View
    get() {
        return getButton(AlertDialog.BUTTON_POSITIVE)
    }