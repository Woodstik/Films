package com.example.films.di

import com.example.films.data.sources.remote.MockMovieService
import com.example.films.data.sources.remote.MovieService
import org.koin.dsl.module

val remoteModule = module {
    single<MovieService> { MockMovieService() }
}
