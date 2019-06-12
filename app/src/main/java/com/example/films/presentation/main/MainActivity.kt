package com.example.films.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.films.R
import com.example.films.presentation.discover.DiscoverFragment
import com.example.films.presentation.home.HomeFragment
import com.example.films.presentation.lists.ListsFragment
import com.example.films.presentation.profile.ProfileFragment
import com.example.films.utils.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*

fun Context.mainIntent(): Intent {
    return Intent(this, MainActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)
        navigation.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            showTitle(menuItem.itemId)
            showFragment(menuItem.itemId)
            true
        }
        navigation.selectedItemId = R.id.item_home
    }

    private fun showFragment(menuId: Int) {
        when (menuId) {
            R.id.item_home -> replaceFragment(R.id.fragmentContainer, HomeFragment.newInstance())
            R.id.item_discover -> replaceFragment(R.id.fragmentContainer, DiscoverFragment.newInstance())
            R.id.item_lists -> replaceFragment(R.id.fragmentContainer, ListsFragment.newInstance())
            R.id.item_profile -> replaceFragment(R.id.fragmentContainer, ProfileFragment.newInstance())
        }
    }

    private fun showTitle(menuId: Int) {
        when (menuId) {
            R.id.item_home -> supportActionBar!!.setTitle(R.string.menu_item_home)
            R.id.item_discover -> supportActionBar!!.setTitle(R.string.menu_item_discover)
            R.id.item_lists -> supportActionBar!!.setTitle(R.string.menu_item_lists)
            R.id.item_profile -> supportActionBar!!.setTitle(R.string.menu_item_profile)
        }
    }
}
