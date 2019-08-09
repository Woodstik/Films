package com.example.films.data.sources.remote

import com.example.films.data.models.MovieList
import com.example.films.data.models.MovieReminder
import io.reactivex.Single

interface MovieListsService {
    fun getReminders(): Single<List<MovieReminder>>
    fun getMovieLists(): Single<List<MovieList>>
}
