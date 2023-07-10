package com.example.busbooking.views

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.busbooking.R
import com.example.busbooking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val homeFragment = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.sourceToolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, homeFragment,"HomeFragment").addToBackStack("HomeFragment").commit()
        }

    }

    override fun onResume() {
        super.onResume()
        checkTopAppBar()
        supportFragmentManager.addOnBackStackChangedListener { checkTopAppBar() }

    }

    private fun checkTopAppBar() {
        val topFrag = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        if (topFrag is HomeFragment) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }else{
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFragmentManager.popBackStack()
        }
        return true
    }
}