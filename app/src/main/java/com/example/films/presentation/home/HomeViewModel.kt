package com.example.films.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.HomeMovies
import com.example.films.data.models.Movie
import com.example.films.data.sources.MovieDataSource
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val movieDataSource: MovieDataSource) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val movies: MutableLiveData<LoadState<HomeMovies>> by lazy {
        MutableLiveData<LoadState<HomeMovies>>()
    }

    fun loadMovies() {
        val disposable = Flowable.zip(
            movieDataSource.getNewReleases(),
            movieDataSource.getUpcomingMovies(),
            movieDataSource.getPopularMovies(),
            Function3 { newReleases: List<Movie>, upcomingMovies: List<Movie>, popularMovies: List<Movie> ->
                HomeMovies(newReleases, upcomingMovies, popularMovies)
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
