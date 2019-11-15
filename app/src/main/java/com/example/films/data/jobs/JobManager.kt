package com.example.films.data.jobs

import com.example.films.data.models.Movie
import java.util.*

interface JobManager {
    fun scheduleReminder(movie: Movie, remindDate: Date):Boolean
}