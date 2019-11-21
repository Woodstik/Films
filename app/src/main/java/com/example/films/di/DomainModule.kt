package com.example.films.di

import com.example.films.domain.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {
    factory { GetHomeUseCase(get(), get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { SearchMoviesUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { GetUserListsUseCase(get(), get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { GetMovieListsUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { CreateMovieListUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { AddMovieToListUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { CreateReminderUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { DeleteRemindersUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { GetRemindersUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { GetTodayRemindersUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
}
