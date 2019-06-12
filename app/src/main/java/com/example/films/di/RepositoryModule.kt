package com.example.films.di

import com.example.films.data.sources.MovieDataSource
import com.example.films.data.sources.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieDataSource> { MovieRepository(get()) }
}
