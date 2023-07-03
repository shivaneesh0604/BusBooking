package com.example.busbooking.dataclass

import com.example.busbooking.enums.Areas
import com.example.busbooking.enums.BusType

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
