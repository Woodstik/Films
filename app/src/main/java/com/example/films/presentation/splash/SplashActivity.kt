package com.example.films.presentation.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.films.presentation.main.mainIntent

fun Context.splashIntent(): Intent {
    return Intent(this, SplashActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
}

class SplashActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(mainIntent())
        finish()
    }
}
