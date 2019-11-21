package com.example.films.jobs

import android.app.job.JobScheduler
import android.content.Context
import android.content.Context.JOB_SCHEDULER_SERVICE
import com.example.films.data.models.Movie
import java.util.*

class AppJobs(val context: Context) : JobManager {

    private val jobScheduler: JobScheduler = context.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

    override fun scheduleRemindersJob(): Boolean {
        return if(jobExists(ReminderJob.REMINDER_JOB_ID)){
            false
        } else {
            jobScheduler.schedule(ReminderJob.build(context)) == JobScheduler.RESULT_SUCCESS
        }
    }

    private fun jobExists(jobId: Int) : Boolean {
        for(job in jobScheduler.allPendingJobs){
            if(job.id == jobId) {
                return true
            }
        }
        return false
    }
}