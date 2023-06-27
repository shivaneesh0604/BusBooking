package com.example.busbooking.DataClass

import android.media.Rating
import com.example.busbooking.Enums.Areas
import com.example.busbooking.Enums.BusType

data class TripDetails(
    val boardingArea: Areas,
    val droppingArea: Areas,
    val travelsName: String,
    val busType: BusType,
    val tripPrice: Int,
    val busRating: Float,
    val dateSelectedTrip: String,
    val boardingTime: String,
    val droppingTIme: String
)
