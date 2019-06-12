package com.example.films.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.HomeMovies
import com.example.films.data.models.Movie
import com.example.films.data.sources.MovieDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import io.reactivex.rxkotlin.subscribeBy

class HomeViewModel(
    private val movieDataSource: MovieDataSource,
    private val schedulerIo: Scheduler,
    private val schedulerMainThread: Scheduler
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val movies: MutableLiveData<LoadState<HomeMovies>> by lazy { MutableLiveData<LoadState<HomeMovies>>() }

    fun loadMovies() {
        val disposable = Flowable.zip(
            movieDataSource.getNewReleases(),
            movieDataSource.getUpcomingMovies(),
            movieDataSource.getPopularMovies(),
            Function3 { newReleases: List<Movie>, upcomingMovies: List<Movie>, popularMovies: List<Movie> ->
                HomeMovies(newReleases, upcomingMovies, popularMovies)
            })
            .subscribeOn(schedulerIo)
            .observeOn(schedulerMainThread)
            .subscribeBy(
                onNext = { movies.value = LoadState.Data(it) },
                onError = { movies.value = LoadState.Error() }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
