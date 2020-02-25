package com.example.films.di

import com.example.films.data.sources.remote.MovieListsService
import com.example.films.data.sources.remote.MovieService
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
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
            .client(get<OkHttpClient>())
            .build()
    }
    single { get<Retrofit>().create(MovieService::class.java) }
    single { get<Retrofit>().create(MovieListsService::class.java) }
}
