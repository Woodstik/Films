package com.example.films.presentation.movielist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import com.example.films.domain.GetMovieListUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class MovieListViewModel(private val getMovieListUseCase: GetMovieListUseCase) : ViewModel() {

    val movieListState = MutableLiveData<LoadState<MovieList>>()
    private val compositeDisposable = CompositeDisposable()

    fun loadMovieList(listId: Long) {
        val disposable = getMovieListUseCase.execute(listId)
            .doOnSubscribe { movieListState.value = LoadState.Loading }
            .subscribeBy (
                onNext = { movieListState.value = LoadState.Data(it) },
                onError = { movieListState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}