package com.example.films.di

import com.example.films.data.sources.MovieDataSource
import com.example.films.data.sources.MovieListDataSource
import com.example.films.data.sources.PostDataSource
import com.example.films.data.sources.ReminderDataSource
import com.example.films.data.sources.repositories.MovieListRepository
import com.example.films.data.sources.repositories.MovieRepository
import com.example.films.data.sources.repositories.PostRepository
import com.example.films.data.sources.repositories.ReminderRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieDataSource> { MovieRepository(get()) }
    single<MovieListDataSource> { MovieListRepository(get()) }
    single<PostDataSource> { PostRepository(get()) }
    single<ReminderDataSource> { ReminderRepository(get()) }
}
