package com.example.films.data.sources

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import io.reactivex.Flowable

interface MovieListDataSource {
    fun getNextReminder(): Flowable<MovieReminder>
    fun getMovieLists(): Flowable<List<MovieList>>
}