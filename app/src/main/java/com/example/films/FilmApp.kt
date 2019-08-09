package com.example.films

import android.app.Application
import com.example.films.di.*
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
    }
}
