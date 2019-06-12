package com.example.films

import android.app.Application
import com.example.films.di.appModule
import com.example.films.di.remoteModule
import com.example.films.di.repositoryModule
import com.example.films.di.viewModelModule
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
            modules(listOf(appModule, remoteModule, repositoryModule, viewModelModule))
        }
    }
}
