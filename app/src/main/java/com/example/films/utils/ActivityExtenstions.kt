package com.example.films.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.films.R
import com.example.films.data.enums.ErrorReason


fun AppCompatActivity.replaceFragment(containerId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(containerId, fragment).commit()
}

fun FragmentActivity.showDialogFragment(dialogFragment: DialogFragment, tag:String = "dialog") {
    val transaction = supportFragmentManager?.beginTransaction()
    val prev = supportFragmentManager?.findFragmentByTag(tag)
    if(prev != null){
        transaction?.remove(prev)
    }
    transaction?.addToBackStack(null)
    dialogFragment.show(supportFragmentManager, tag)
}

fun Context.openUrl(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}