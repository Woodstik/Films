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
import androidx.core.app.NotificationCompat
import com.example.films.BuildConfig
import com.example.films.R
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.DeleteRemindersRequest
import com.example.films.domain.DeleteRemindersUseCase
import com.example.films.domain.GetTodayRemindersUseCase
import com.example.films.presentation.splash.splashIntent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.ext.android.inject
import java.util.*


class ReminderJob : JobService() {

    private val getTodayRemindersUseCase: GetTodayRemindersUseCase by inject()
    private val deleteRemindersUseCase: DeleteRemindersUseCase by inject()
    private val compositeDisposable = CompositeDisposable()
    private val notifyManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        createNotificationChannel()
        val disposable = getTodayRemindersUseCase.execute()
            .filter { it.isNotEmpty() }
            .doOnNext { showNotification(it) }
            .map { it.map { reminder -> reminder.id } }
            .flatMap { deleteRemindersUseCase.execute(DeleteRemindersRequest(it)) }
            .subscribeBy(
                onError = { jobFinished(params, true) },
                onComplete = { jobFinished(params, false) }
            )
        compositeDisposable.add(disposable)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        compositeDisposable.clear()
        return false
    }

    private fun showNotification(reminders: List<MovieReminder>) {
        val pendingIntent = PendingIntent.getActivity(this, 0, splashIntent(), PendingIntent.FLAG_UPDATE_CURRENT)
        val titles = reminders.map { it.movie.title }
        val notification = NotificationCompat.Builder(this, REMINDERS_CHANNEL_ID)
            .setContentTitle(
                resources.getQuantityString(
                    R.plurals.notification_title_movie_released,
                    reminders.size,
                    reminders.size
                )
            )
            .setContentText(titles.joinToString())
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notifyManager.notify(REMINDER_JOB_ID, notification)
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

    companion object {
        private const val REMINDERS_CHANNEL_ID = "reminders_channel"
        const val REMINDER_JOB_ID = 1

        fun build(context: Context): JobInfo {
            val serviceName = ComponentName(
                context.packageName,
                ReminderJob::class.java.name
            )
            return JobInfo.Builder(REMINDER_JOB_ID, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(false)
                .setPersisted(true)
                .setPeriodic(BuildConfig.REMINDERS_JOB_PERIOD)
                .build()
        }
    }
}