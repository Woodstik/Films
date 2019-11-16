package com.example.films.jobs

import android.app.job.JobScheduler
import android.content.Context
import android.content.Context.JOB_SCHEDULER_SERVICE
import com.example.films.data.models.Movie
import java.util.*

class AppJobs(val context: Context) : JobManager {

    private val jobScheduler: JobScheduler = context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

    override fun scheduleReminder(reminderId: Long, movie: Movie, remindDate: Date) : Boolean {
        return jobScheduler.schedule(reminderJob(reminderId, movie, remindDate)) == JobScheduler.RESULT_SUCCESS
    }
}