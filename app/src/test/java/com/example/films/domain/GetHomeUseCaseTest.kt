package com.example.films.domain

import com.example.films.TestData
import com.example.films.data.sources.remote.MockMovieService
import com.example.films.data.sources.remote.MockPostService
import com.example.films.data.sources.repositories.MovieRepository
import com.example.films.data.sources.repositories.PostRepository
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class GetHomeUseCaseTest{

    @Test
    fun getHome(){
        val scheduler = Schedulers.trampoline()
        val getHomeUseCase = GetHomeUseCase(MovieRepository(MockMovieService(TestData.Movies)), PostRepository(MockPostService()), scheduler, scheduler)
        getHomeUseCase.execute()
            .test()
            .assertComplete()
    }
}
