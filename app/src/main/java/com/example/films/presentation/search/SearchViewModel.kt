package com.example.films.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.Movie
import com.example.films.domain.SearchMoviesUseCase
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchViewModel(private val searchMoviesUseCase: SearchMoviesUseCase) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val movies: MutableLiveData<LoadState<List<Movie>>> by lazy { MutableLiveData<LoadState<List<Movie>>>() }

    init {
        val search : (String) -> Unit = { query ->
            val disposable = searchMoviesUseCase.execute(query)
                .doOnSubscribe { movies.value = LoadState.Loading }
                .subscribeBy(
                    onNext = { movies.value = LoadState.Data(it) },
                    onError = { movies.value = LoadState.Error(it) }
                )
            compositeDisposable.add(disposable)
        }
        compositeDisposable.add(searchMoviesUseCase.setUp(search))
    }

    fun queryChanged(query: String) {
        searchMoviesUseCase.search(query)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}
