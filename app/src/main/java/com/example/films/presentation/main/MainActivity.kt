package com.example.films.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.films.R
import com.example.films.presentation.discover.DiscoverFragment
import com.example.films.presentation.home.HomeFragment
import com.example.films.presentation.lists.ListsFragment
import com.example.films.presentation.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

fun Context.mainIntent(): Intent {
    return Intent(this, MainActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.item_home -> showFragment(HomeFragment.newInstance())
                R.id.item_discover -> showFragment(DiscoverFragment.newInstance())
                R.id.item_lists -> showFragment(ListsFragment.newInstance())
                R.id.item_profile -> showFragment(ProfileFragment.newInstance())
                else -> false
            }
        }
        navigation.selectedItemId = R.id.item_home
    }

    private fun showFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
        return true
    }
}
