package com.example.films.jobs

import com.example.films.data.models.Movie
import java.util.*

interface JobManager {
    fun scheduleReminder(reminderId:Long, movie: Movie, remindDate: Date):Boolean
}