package com.example.films.di

import com.example.films.presentation.home.HomeViewModel
import com.example.films.presentation.userlists.UserListsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { UserListsViewModel(get()) }
}
