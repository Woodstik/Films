package com.example.films.presentation.movielist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import com.example.films.data.requests.DeleteMovieFromListRequest
import com.example.films.data.requests.EditListRequest
import com.example.films.domain.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class MovieListViewModel(
    private val getMovieListUseCase: GetMovieListUseCase,
    private val deleteMovieFromListUseCase: DeleteMovieFromListUseCase,
    private val deleteMovieListUseCase: DeleteMovieListUseCase
) : ViewModel() {

    val movieListState = MutableLiveData<LoadState<MovieList>>()
    val deleteMovieListState = MutableLiveData<LoadState<Unit>>()
    private val compositeDisposable = CompositeDisposable()

    fun loadMovieList(listId: Long) {
        val disposable = getMovieListUseCase.execute(listId)
            .doOnSubscribe { movieListState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { movieListState.value = LoadState.Data(it) },
                onError = { movieListState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    fun deleteMovie(listId: Long, movieId: Int) {
        val disposable =
            deleteMovieFromListUseCase.execute(DeleteMovieFromListRequest(listId, movieId))
                .flatMap { getMovieListUseCase.execute(listId) }
                .doOnSubscribe { movieListState.value = LoadState.Loading }
                .subscribeBy(
                    onNext = { movieListState.value = LoadState.Data(it) },
                    onError = { movieListState.value = LoadState.Error(it) }
                )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun deleteList(listId: Long) {
        val disposable = deleteMovieListUseCase.execute(listId)
            .doOnSubscribe { deleteMovieListState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { deleteMovieListState.value = LoadState.Data(it) },
                onError = { deleteMovieListState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }
}