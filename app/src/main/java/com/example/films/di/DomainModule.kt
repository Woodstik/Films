package com.example.films.di

import com.example.films.domain.GetHomeUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module{
    factory { GetHomeUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
}
