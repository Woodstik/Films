package com.example.films

import android.app.Application
import com.example.films.data.sources.MovieDataSource
import com.example.films.data.sources.MovieRepository
import com.example.films.di.homeModule
import com.example.films.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class FilmApp : Application() {

    private val appModule = module {
        single<MovieDataSource> { MovieRepository(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@FilmApp)
            modules(listOf(remoteModule, appModule, homeModule))
        }
    }
}
