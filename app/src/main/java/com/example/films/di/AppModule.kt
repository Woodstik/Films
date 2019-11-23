package com.example.films.di

import com.example.films.BuildConfig
import com.example.films.jobs.AppJobs
import com.example.films.jobs.JobManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val SCHEDULER_IO = "SCHEDULER_IO"
const val SCHEDULER_MAIN_THREAD = "SCHEDULER_MAIN_THREAD"
const val SCHEDULER_COMPUTATION = "SCHEDULER_COMPUTATION"

val appModule = module {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
    single {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
    }
    single(named(SCHEDULER_IO)) { Schedulers.io() }
    single(named(SCHEDULER_MAIN_THREAD)) { AndroidSchedulers.mainThread() }
    single(named(SCHEDULER_COMPUTATION)) { Schedulers.computation() }
    single<JobManager> { AppJobs(androidContext()) }
}
