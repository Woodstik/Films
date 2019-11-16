package com.example.films.jobs

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.os.PersistableBundle
import androidx.core.app.NotificationCompat
import com.example.films.R
import com.example.films.data.models.Movie
import com.example.films.data.requests.RemoveReminderRequest
import com.example.films.domain.RemoveReminderUseCase
import com.example.films.presentation.splash.splashIntent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.*


private const val REMINDERS_CHANNEL_ID = "reminders_channel"

private const val EXTRA_MOVIE_TITLE = "extra_movie_title"
private const val EXTRA_REMINDER_ID = "extra_reminder_id"

fun AppJobs.reminderJob(reminderId: Long, movie: Movie, remindDate: Date) : JobInfo{
    val serviceName = ComponentName(
        context.packageName,
        ReminderJob::class.java.name
    )
    val bundle = PersistableBundle()
    bundle.putLong(EXTRA_REMINDER_ID, reminderId)
    bundle.putString(EXTRA_MOVIE_TITLE, movie.title)
    val remindMillis = remindDate.time - Date().time
    return JobInfo.Builder(movie.id, serviceName)
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        .setRequiresDeviceIdle(false)
        .setRequiresCharging(false)
        .setPersisted(true)
        .setExtras(bundle)
        .setMinimumLatency(remindMillis)
        .build()
}

class ReminderJob : JobService() {

    private val removeReminderUseCase : RemoveReminderUseCase by inject()
    private val compositeDisposable = CompositeDisposable()
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
        val reminderId = extras.getLong(EXTRA_REMINDER_ID)
        val disposable = removeReminderUseCase.execute(RemoveReminderRequest(reminderId))
            .subscribeBy (
                onNext = {
                    val builder = NotificationCompat.Builder(this, REMINDERS_CHANNEL_ID)
                        .setContentTitle(getString(R.string.notification_title_movie_released))
                        .setContentText(extras.getString(EXTRA_MOVIE_TITLE))
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_movie_reminder)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setAutoCancel(true)
                    notifyManager.notify(reminderId.toInt(), builder.build())
                    jobFinished(params, false)
                },
                onError = {
                    Timber.e(it, "Error removing reminder!")
                    jobFinished(params, true)
                }
            )
        compositeDisposable.add(disposable)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        compositeDisposable.clear()
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