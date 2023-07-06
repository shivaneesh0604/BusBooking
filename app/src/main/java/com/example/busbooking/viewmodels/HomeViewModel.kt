package com.example.busbooking.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel

class HomeViewModel:ViewModel() {
    var selectedDay : String?= null
    var selectedSource : String ? = null
    var selectedDestination: String?=null
}