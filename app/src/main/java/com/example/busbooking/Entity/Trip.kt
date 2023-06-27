package com.example.busbooking.Entity

import com.example.busbooking.Enums.Areas

data class Trip(
    val tripID: Int,
    val busID: Int,
    val driverID: Int,
    val boardingArea: Areas,
    val droppingArea: Areas,
    val travelDateFrom: String,
    val travelDateTo: String,
    val perSeatPrice: Int,
    val boardingTime: String,
    val droppingTime: String
)