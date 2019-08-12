package com.example.films.di

import com.example.films.data.sources.remote.*
import org.koin.dsl.module

val remoteModule = module {
    single<MovieService> { MockMovieService() }
    single<MovieListsService> { MockMovieListsService() }
    single<PostService> { MockPostService() }
}
