package com.example.films.di

import com.example.films.data.sources.remote.MovieService
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
    single { getMovieService(get()) }
}

private fun getMovieService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)
