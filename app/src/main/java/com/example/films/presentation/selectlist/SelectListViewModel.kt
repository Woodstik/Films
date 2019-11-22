package com.example.films.presentation.selectlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.domain.AddMovieToListUseCase
import com.example.films.domain.GetMovieListsUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class SelectListViewModel(
    private val getMovieListsUseCase: GetMovieListsUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val movieListsState: MutableLiveData<LoadState<List<MovieList>>> by lazy { MutableLiveData<LoadState<List<MovieList>>>() }
    val addMovieToListState: MutableLiveData<LoadState<Unit>> by lazy { MutableLiveData<LoadState<Unit>>() }

    fun loadLists() {
        val disposable = getMovieListsUseCase.execute()
            .doOnSubscribe { movieListsState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { movieListsState.value = LoadState.Data(it) },
                onError = { movieListsState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    fun addMovieToList(movieId: Int, listId: Long) {
        val disposable = addMovieToListUseCase.execute(AddMovieToListRequest(movieId, listId))
            .doOnSubscribe { addMovieToListState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { addMovieToListState.value = LoadState.Data(it) },
                onError = { addMovieToListState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}