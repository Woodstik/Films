package com.example.films

import android.app.Application
import com.example.films.di.*
import com.example.films.jobs.JobManager
import com.google.android.gms.ads.MobileAds
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class FilmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@FilmApp)
            modules(listOf(appModule, remoteModule, localModule, repositoryModule, domainModule, viewModelModule))
        }

        val jobManager: JobManager = get()
        jobManager.scheduleRemindersJob()

        MobileAds.initialize(this, getString(R.string.ad_mob_app_id))
    }
}
