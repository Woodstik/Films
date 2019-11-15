package com.example.films.jobs

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.graphics.Color
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import com.example.films.R
import com.example.films.presentation.splash.splashIntent
import android.content.ComponentName
import android.app.job.JobInfo
import android.os.PersistableBundle
import com.example.films.data.models.Movie
import java.util.*


private const val REMINDERS_CHANNEL_ID = "reminders_channel"

private const val EXTRA_MOVIE_TITLE = "extra_movie_title"
private const val EXTRA_MOVIE_ID = "extra_movie_id"

fun AppJobs.reminderJob(movie: Movie, remindDate: Date) : JobInfo{
    val serviceName = ComponentName(
        context.packageName,
        ReminderJob::class.java.name
    )
    val bundle = PersistableBundle()
    bundle.putInt(EXTRA_MOVIE_ID, movie.id)
    bundle.putString(EXTRA_MOVIE_TITLE, movie.title)
    val remindMillis = remindDate.time - Date().time
    return JobInfo.Builder(movie.id, serviceName)
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
        .setRequiresDeviceIdle(false)
        .setRequiresCharging(false)
        .setPersisted(true)
        .setExtras(bundle)
        .setMinimumLatency(remindMillis)
        .build()
}

class ReminderJob : JobService() {

    private val notifyManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        createNotificationChannel()
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            splashIntent(),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val extras = params!!.extras
        val builder = NotificationCompat.Builder(this, REMINDERS_CHANNEL_ID)
            .setContentTitle(getString(R.string.movie_released))
            .setContentText(extras.getString(EXTRA_MOVIE_TITLE))
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_movie_reminder)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)

        notifyManager.notify(extras.getInt(EXTRA_MOVIE_ID), builder.build())
        //TODO: Remove reminder from list
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                REMINDERS_CHANNEL_ID,
                getString(R.string.channel_reminders_name),
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.channel_reminders_description)
            notifyManager.createNotificationChannel(notificationChannel)
        }
    }
}