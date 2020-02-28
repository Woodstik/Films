package com.example.films.presentation.signup

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.films.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.dark_toolbar.*

fun Context.signUpIntent() = Intent(this, SignUpActivity::class.java)

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setSupportActionBar(toolbar)
        setTitle(R.string.sign_up_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorWindowBackground)

        val alreadyHaveAccount = getString(R.string.already_have_account)
        val spannableString = SpannableString(alreadyHaveAccount)
        val start = alreadyHaveAccount.indexOf("Log in")
        val end = start + "Log in".length
        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this,
                    R.color.colorAccent
                )
            ), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            end,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textAlreadyHaveAccount.text = spannableString
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