package com.example.films.di

import com.example.films.data.sources.MovieDataSource
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.repositories.MovieListRepository
import com.example.films.data.sources.repositories.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieDataSource> { MovieRepository(get()) }
    single<MovieListDataSource> { MovieListRepository(get()) }
}
