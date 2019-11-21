package com.example.films.jobs

interface JobManager {
    fun scheduleRemindersJob(): Boolean
}