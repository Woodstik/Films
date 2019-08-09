package com.example.films.presentation.userlists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.UsersMovieLists
import com.example.films.domain.GetUserListsUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class UserListsViewModel(private val getUserListsUseCase: GetUserListsUseCase) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val usersMovieLists: MutableLiveData<LoadState<UsersMovieLists>> by lazy { MutableLiveData<LoadState<UsersMovieLists>>() }

    fun loadLists() {
        val disposable = getUserListsUseCase.execute()
            .doOnSubscribe { usersMovieLists.value = LoadState.Loading() }
            .subscribeBy(
                onNext = { usersMovieLists.value = LoadState.Data(it) },
                onError = { usersMovieLists.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
