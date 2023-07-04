package com.example.busbooking.views

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.sourceToolbar)

        if (savedInstanceState != null) {
            Log.e("check", "if in main")
            val fragmentTag = savedInstanceState.getString("fragmentTag")
            val currentFragment = supportFragmentManager.findFragmentByTag(fragmentTag)
            if (currentFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, currentFragment)
                    .commit()

//                supportFragmentManager.addOnBackStackChangedListener { checkUpBottomNavAndTopAppBar() }
            }
        } else {
            Log.e("check", "else in main")
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, HomeFragment()).addToBackStack(null).commit()
//            checkUpBottomNavAndTopAppBar()
        }

    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        checkUpBottomNavAndTopAppBar()
        supportFragmentManager.addOnBackStackChangedListener { checkUpBottomNavAndTopAppBar() }

    }

    private fun checkUpBottomNavAndTopAppBar() {
        val topFrag = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        Log.e("check", "" + topFrag)

        if (topFrag is HomeFragment) {
            activityMainBinding.sourceToolbar.visibility = View.GONE
        } else {
            activityMainBinding.sourceToolbar.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFragmentManager.popBackStack()
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        if (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) != null) {
            outState.putString(
                "fragmentTag",
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.tag
            )
        }
    }
}