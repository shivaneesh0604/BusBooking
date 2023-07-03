package com.example.busbooking.entity

import com.example.busbooking.enums.Areas

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