package com.example.films.di

import com.example.films.presentation.createlist.CreateListViewModel
import com.example.films.presentation.home.HomeViewModel
import com.example.films.presentation.profile.ProfileViewModel
import com.example.films.presentation.reminders.RemindersViewModel
import com.example.films.presentation.search.SearchViewModel
import com.example.films.presentation.selectlist.SelectListViewModel
import com.example.films.presentation.userlists.UserListsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { UserListsViewModel(get()) }
    viewModel { ProfileViewModel() }
    viewModel { SearchViewModel(get()) }
    viewModel { SelectListViewModel(get(), get()) }
    viewModel { CreateListViewModel(get(), get()) }
    viewModel { RemindersViewModel(get(), get()) }
}
