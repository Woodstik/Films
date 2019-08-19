package com.example.films.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.films.R
import com.example.films.presentation.home.HomeFragment
import com.example.films.presentation.profile.ProfileFragment
import com.example.films.presentation.search.searchIntent
import com.example.films.presentation.userlists.UserListsFragment
import com.example.films.utils.replaceFragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

fun Context.mainIntent(): Intent {
    return Intent(this, MainActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorWindowBackground)
        navigation.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            //            showTitle(menuItem.itemId)
            showFragment(menuItem.itemId)
            true
        }
        navigation.selectedItemId = R.id.item_home
        cardSearch.setOnClickListener { startActivity(searchIntent()) }
        setUpAd()
    }

    private fun showFragment(menuId: Int) {
        when (menuId) {
            R.id.item_home -> replaceFragment(R.id.fragmentContainer, HomeFragment.newInstance())
            R.id.item_lists -> replaceFragment(R.id.fragmentContainer, UserListsFragment.newInstance())
            R.id.item_profile -> replaceFragment(R.id.fragmentContainer, ProfileFragment.newInstance())
        }
    }

    private fun showTitle(menuId: Int) {
        when (menuId) {
            R.id.item_home -> supportActionBar!!.setTitle(R.string.menu_item_home)
            R.id.item_lists -> supportActionBar!!.setTitle(R.string.menu_item_lists)
            R.id.item_profile -> supportActionBar!!.setTitle(R.string.menu_item_profile)
        }
    }

    private fun setUpAd() {
        adView.loadAd(AdRequest.Builder().build())
        adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                adView.visibility = View.VISIBLE
            }

            override fun onAdFailedToLoad(errorCode : Int) {
                // Code to be executed when an ad request fails.
                Timber.e("onAddFailedToLoad: $errorCode")
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }
}
