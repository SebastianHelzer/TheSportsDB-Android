package com.seboba.sports

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seboba.sports.ui.fragments.FavoritesFragment
import com.seboba.sports.ui.search.MainFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }

        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitNow()
                }
                R.id.action_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FavoritesFragment.newInstance())
                        .commitNow()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
