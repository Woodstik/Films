package com.example.films.presentation.auth

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.films.R
import com.example.films.presentation.login.loginIntent
import com.example.films.presentation.signup.signUpIntent
import kotlinx.android.synthetic.main.activity_auth_menu.*
import kotlinx.android.synthetic.main.dark_toolbar.*

fun Context.authMenuIntent() = Intent(this, AuthMenuActivity::class.java)

class AuthMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_menu)
        setSupportActionBar(toolbar)
        title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorWindowBackground)


        val logoAnimator = ObjectAnimator.ofPropertyValuesHolder(
            imgLogo,
            PropertyValuesHolder.ofFloat("scaleX", 1.05f),
            PropertyValuesHolder.ofFloat("scaleY", 1.05f)
        )
        logoAnimator.duration = 3500
        logoAnimator.repeatCount = ObjectAnimator.INFINITE
        logoAnimator.repeatMode = ObjectAnimator.REVERSE
        val logoBackgroundAnimator = ObjectAnimator.ofPropertyValuesHolder(
            imgLogoBackground,
            PropertyValuesHolder.ofFloat("scaleX", 1.1f),
            PropertyValuesHolder.ofFloat("scaleY", 1.1f)
        )
        logoBackgroundAnimator.duration = 3500
        logoBackgroundAnimator.repeatCount = ObjectAnimator.INFINITE
        logoBackgroundAnimator.repeatMode = ObjectAnimator.REVERSE

        logoAnimator.start()
        logoBackgroundAnimator.start()

        btnLogin.setOnClickListener { startActivity(loginIntent()) }
        btnSignUp.setOnClickListener { startActivity(signUpIntent()) }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}