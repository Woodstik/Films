package com.example.films.di

import com.example.films.TestData
import com.example.films.data.sources.remote.*
import org.koin.dsl.module

val remoteModule = module {
    single<MovieService> { MockMovieService(TestData.Movies) }
    single<MovieListsService> { MockMovieListsService(TestData.Movies) }
    single<PostService> { MockPostService() }
}
