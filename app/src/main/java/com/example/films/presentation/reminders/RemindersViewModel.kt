package com.example.films.presentation.reminders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieReminder
import com.example.films.data.requests.DeleteRemindersRequest
import com.example.films.domain.DeleteRemindersUseCase
import com.example.films.domain.GetRemindersUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class RemindersViewModel(
    private val getRemindersUseCase: GetRemindersUseCase,
    private val deleteRemindersUseCase: DeleteRemindersUseCase
) : ViewModel() {

    val remindersState: MutableLiveData<LoadState<List<MovieReminder>>> by lazy { MutableLiveData<LoadState<List<MovieReminder>>>() }
    val deleteReminderState: MutableLiveData<LoadState<Unit>> by lazy { MutableLiveData<LoadState<Unit>>() }
    private val compositeDisposable = CompositeDisposable()

    fun loadReminders(){
        val disposable = getRemindersUseCase.execute()
            .doOnSubscribe { remindersState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { remindersState.value = LoadState.Data(it) },
                onError = { remindersState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    fun deleteReminder(reminderId: Long){
        val disposable = deleteRemindersUseCase.execute(DeleteRemindersRequest(listOf(reminderId)))
            .doOnSubscribe { deleteReminderState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { deleteReminderState.value = LoadState.Data(it) },
                onError = { deleteReminderState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}