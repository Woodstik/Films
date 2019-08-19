package com.example.films.di

import com.example.films.domain.GetHomeUseCase
import com.example.films.domain.GetUserListsUseCase
import com.example.films.domain.SearchMoviesUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {
    factory { GetHomeUseCase(get(), get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { GetUserListsUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { SearchMoviesUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
}
