package com.example.films.data.sources.remote

import io.reactivex.Completable

interface AuthService {

    fun signUp(): Completable
    fun login(): Completable
    fun forgotPassword(): Completable
    fun changePassword(): Completable
}