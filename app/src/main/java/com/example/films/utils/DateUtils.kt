package com.example.films.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatReleaseDate(releaseDate: Date): String = formatDate(releaseDate, "MMMM d, yyyy")

fun formatDate(date: Date, format: String): String {
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return simpleDateFormat.format(date)
}
