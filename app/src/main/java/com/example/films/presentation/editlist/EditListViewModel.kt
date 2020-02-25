package com.example.films.presentation.editlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.films.data.enums.LoadState
import com.example.films.data.models.MovieList
import com.example.films.data.requests.AddMovieToListRequest
import com.example.films.data.requests.CreateMovieListRequest
import com.example.films.data.requests.EditListRequest
import com.example.films.domain.AddMovieToListUseCase
import com.example.films.domain.CreateMovieListUseCase
import com.example.films.domain.EditListUseCase
import com.example.films.domain.GetMovieListUseCase
import com.example.films.utils.randomListColor
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class EditListViewModel(
    private val createMovieListUseCase: CreateMovieListUseCase,
    private val addMovieToListUseCase: AddMovieToListUseCase,
    private val getMovieListUseCase: GetMovieListUseCase,
    private val editListUseCase: EditListUseCase
) : ViewModel() {

    val movieListState = MutableLiveData<LoadState<MovieList>>()
    val submitListState: MutableLiveData<LoadState<Unit>> by lazy { MutableLiveData<LoadState<Unit>>() }
    private val compositeDisposable = CompositeDisposable()

    private var listId: Long = 0
    private var movieId: Int = 0


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun initialize(movieId: Int, listId: Long) {
        this.listId = listId
        this.movieId = movieId
        if(listId > 0){
            loadList()
        }
    }

    fun submit(title: String){
        if (listId > 0){
            renameList(listId, title)
        } else {
            createMovieList(title, movieId)
        }
    }

    private fun createMovieList(title: String, movieId: Int, color: String = randomListColor()) {
        val disposable = createMovieListUseCase.execute(CreateMovieListRequest(title, color))
            .flatMap {
                if (movieId > 0 ) {
                    addMovieToListUseCase.execute(AddMovieToListRequest(movieId, it))
                } else {
                    Flowable.just(Unit)
                }
            }
            .doOnSubscribe { submitListState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { submitListState.value = LoadState.Data(it) },
                onError = { submitListState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    private fun renameList(listId: Long, newTitle: String) {
        val disposable = editListUseCase.execute(EditListRequest(listId, title = newTitle))
            .doOnSubscribe { submitListState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { submitListState.value = LoadState.Data(it) },
                onError = { submitListState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    private fun loadList() {
        val disposable = getMovieListUseCase.execute(listId)
            .doOnSubscribe { movieListState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { movieListState.value = LoadState.Data(it) },
                onError = { movieListState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }
}