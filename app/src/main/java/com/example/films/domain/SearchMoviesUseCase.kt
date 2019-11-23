package com.example.films.domain

import com.example.films.data.models.Movie
import com.example.films.data.sources.MovieDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchMoviesUseCase(
    private val movieDataSource: MovieDataSource,
    schedulerIO: Scheduler,
    private val schedulerMainThread: Scheduler,
    private val schedulerDebounce: Scheduler
) : UseCase<String, List<Movie>>(schedulerIO, schedulerMainThread) {

    private val searchSubject = PublishSubject.create<String>()

    fun setUp(doSearch: (String) -> Unit) : Disposable {
        return searchSubject.debounce(SEARCH_DEBOUNCE_TIME, SEARCH_DEBOUNCE_TIME_UNIT, schedulerDebounce)
            .distinctUntilChanged()
            .observeOn(schedulerMainThread)
            .subscribeBy(onNext = { doSearch(it) })
    }

    override fun onCreate(parameter: String?): Flowable<List<Movie>> {
        return if(parameter!!.isNotEmpty()) {
            movieDataSource.search(parameter)
        } else {
            Flowable.just(emptyList())
        }
    }

    fun search(query: String) {
        searchSubject.onNext(query)
    }

    companion object{
        const val SEARCH_DEBOUNCE_TIME = 300L
        val SEARCH_DEBOUNCE_TIME_UNIT = TimeUnit.MILLISECONDS
    }
}
