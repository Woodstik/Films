package com.example.films.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat.startActivity
import android.content.ActivityNotFoundException
import android.R.id
import android.content.Context
import android.content.Intent
import android.net.Uri


fun AppCompatActivity.replaceFragment(containerId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(containerId, fragment).commit()
}

fun Context.openUrl(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}