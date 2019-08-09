package com.example.films.di

import com.example.films.data.sources.remote.MockMovieListsService
import com.example.films.data.sources.remote.MockMovieService
import com.example.films.data.sources.remote.MovieListsService
import com.example.films.data.sources.remote.MovieService
import org.koin.dsl.module

val remoteModule = module {
    single<MovieService> { MockMovieService() }
    single<MovieListsService> { MockMovieListsService() }
}
