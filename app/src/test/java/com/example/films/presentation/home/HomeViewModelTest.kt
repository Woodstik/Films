package com.example.films.presentation.home

import com.example.films.data.sources.remote.MockMovieService
import com.example.films.data.sources.remote.MockPostService
import com.example.films.data.sources.repositories.MovieRepository
import com.example.films.data.sources.repositories.PostRepository
import com.example.films.domain.GetHomeUseCase
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class HomeViewModelTest {

    @Test
    fun loadMovies() {
        val viewModel = HomeViewModel(
            GetHomeUseCase(
                MovieRepository(MockMovieService()),
                PostRepository(MockPostService()),
                Schedulers.trampoline(),
                Schedulers.trampoline()
            )
        )
        viewModel.loadMovies()
    }
}
