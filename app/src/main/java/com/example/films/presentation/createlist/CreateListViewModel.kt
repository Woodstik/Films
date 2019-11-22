package com.example.films.presentation.createlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.domain.AddMovieToListUseCase
import com.example.films.domain.CreateMovieListUseCase
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class CreateListViewModel(
    private val createMovieListUseCase: CreateMovieListUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val createListState: MutableLiveData<LoadState<Unit>> by lazy { MutableLiveData<LoadState<Unit>>() }

    fun createMovieList(title: String, movieId: Int) {
        val disposable = createMovieListUseCase.execute(CreateMovieListRequest(title))
            .flatMap {
                if (movieId > 0 ) {
                    addMovieToListUseCase.execute(AddMovieToListRequest(movieId, it))
                } else {
                    Flowable.just(Unit)
                }
            }
            .doOnSubscribe { createListState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { createListState.value = LoadState.Data(it) },
                onError = { createListState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}