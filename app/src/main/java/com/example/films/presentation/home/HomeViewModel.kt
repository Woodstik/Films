package com.example.films.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.HomeMovies
import com.example.films.domain.GetHomeUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class HomeViewModel(private val getHomeUseCase: GetHomeUseCase) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val movies: MutableLiveData<LoadState<HomeMovies>> by lazy { MutableLiveData<LoadState<HomeMovies>>() }

    fun loadMovies() {
        val disposable = getHomeUseCase.execute()
            .doOnSubscribe { movies.value = LoadState.Loading() }
            .subscribeBy(
                onNext = { movies.value = LoadState.Data(it) },
                onError = { movies.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
