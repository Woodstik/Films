package com.example.films.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.films.presentation.main.mainIntent

class SplashActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(mainIntent())
        finish()
    }
}
