package com.example.films.data.sources

import io.reactivex.Completable

interface AuthDataSource{
    fun signUp() : Completable
    fun login() : Completable
    fun forgotPassword() : Completable
    fun changePassword() : Completable
    fun logout() : Completable
}