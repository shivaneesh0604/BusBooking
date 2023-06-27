package com.example.busbooking.View

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.busbooking.R
import com.example.busbooking.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

//        activityMainBinding.sourceToolbar.visibility =
        setContentView(activityMainBinding.root)

    }

    override fun onResume() {
        super.onResume()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavView = activityMainBinding.bottomNav
        checkUpBottomNavAndTopAppBar()

        bottomNavView.setupWithNavController(navController)
        supportFragmentManager.addOnBackStackChangedListener { checkUpBottomNavAndTopAppBar() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun checkUpBottomNavAndTopAppBar() {
        val topFrag = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (topFrag is NavHostFragment) {
            bottomNavView.visibility = View.VISIBLE
        } else {
            bottomNavView.visibility = View.GONE
        }
        setSupportActionBar(activityMainBinding.sourceToolbar)

        if (topFrag is NavHostFragment) {
            activityMainBinding.sourceToolbar.visibility = View.GONE
        } else {
            activityMainBinding.sourceToolbar.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFragmentManager.popBackStack()
        } else {
            val navController = findNavController(R.id.nav_host_fragment)
            item.onNavDestinationSelected(navController)
        }
        return true
    }

}