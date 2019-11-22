package com.example.films.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.HomeMovies
import com.example.films.data.requests.CreateReminderRequest
import com.example.films.domain.CreateReminderUseCase
import com.example.films.domain.GetHomeUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class HomeViewModel(
    private val getHomeUseCase: GetHomeUseCase,
    private val createReminderUseCase: CreateReminderUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val moviesState: MutableLiveData<LoadState<HomeMovies>> by lazy { MutableLiveData<LoadState<HomeMovies>>() }
    val createReminderState: MutableLiveData<LoadState<Unit>> by lazy { MutableLiveData<LoadState<Unit>>() }

    fun loadMovies() {
        val disposable = getHomeUseCase.execute()
            .doOnSubscribe { moviesState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { moviesState.value = LoadState.Data(it) },
                onError = { moviesState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    fun createReminder(movieId: Int) {
        val disposable = createReminderUseCase.execute(CreateReminderRequest(movieId))
            .doOnSubscribe { createReminderState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { createReminderState.value = LoadState.Data(Unit) },
                onError = { createReminderState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
