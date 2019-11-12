package com.example.films.presentation.selectlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import com.example.films.domain.GetMovieListsUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class SelectListViewModel(private val getMovieListsUseCase: GetMovieListsUseCase) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val movieLists: MutableLiveData<LoadState<List<MovieList>>> by lazy { MutableLiveData<LoadState<List<MovieList>>>() }

    fun loadLists() {
        val disposable = getMovieListsUseCase.execute()
            .doOnSubscribe { movieLists.value = LoadState.Loading() }
            .subscribeBy(
                onNext = { movieLists.value = LoadState.Data(it) },
                onError = { movieLists.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}