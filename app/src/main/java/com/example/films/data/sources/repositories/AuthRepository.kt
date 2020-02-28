package com.example.films.data.sources.repositories

import com.example.films.data.sources.AuthDataSource
import com.example.films.data.sources.remote.AuthService
import io.reactivex.Completable

class AuthRepository(private val authService: AuthService) : AuthDataSource{

    override fun signUp(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun forgotPassword(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changePassword(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logout(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}