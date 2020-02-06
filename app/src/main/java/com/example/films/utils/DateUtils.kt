package com.example.films.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil
import kotlin.math.roundToInt

fun formatReleaseDate(releaseDate: Date): String = formatDate(releaseDate, "MMMM d, yyyy")

fun formatCreatedDate(releaseDate: Date): String = formatDate(releaseDate, "MMMM d, yyyy")

fun formatReleaseDateShort(releaseDate: Date): String = formatDate(releaseDate, "M/d/yyyy")

fun formatReleaseYear(joinedDate: Date): String = formatDate(joinedDate, "yyyy")

fun formatJoinedDate(joinedDate: Date): String = formatDate(joinedDate, "MMMM d, yyyy")

fun formatReminderDate(reminderDate: Date): String = formatDate(reminderDate, "MMMM d, yyyy")

fun formatDate(date: Date, format: String): String {
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return simpleDateFormat.format(date)
}

fun formatPostTime(date: Date): String {
    val now = Date().time
    val timeDifference = (now - date.time).toDouble() / 1000
    println("$timeDifference s")
    return when {
        timeDifference < 60 * 60 -> {
            val min = ceil(timeDifference / 60).toInt()
            "$min min${if (min == 1) "" else "s"}"
        }
        timeDifference < 24 * 60 * 60 -> {
            val hours = (timeDifference / (60 * 60)).roundToInt()
            "$hours hr${if (hours == 1) "" else "s"}"
        }
        timeDifference < 7 * 24 * 60 * 60 -> {
            val days = (timeDifference / (60 * 60 * 24)).roundToInt()
            "$days day${if (days == 1) "" else "s"}"
        }
        timeDifference < 30 * 24 * 60 * 60 -> "${(timeDifference / (60 * 60 * 24 * 7)).roundToInt()} wk"
        timeDifference < 365 * 24 * 60 * 60 -> {
            val months = (timeDifference / (60 * 60 * 24 * 30)).roundToInt()
            "$months month${if (months == 1) "" else "s"}"
        }
        else -> "${(timeDifference / (60 * 60 * 24 * 365)).roundToInt()} yr"
    }
}
