package com.example.busbooking.Entity

data class Seat(
    val seatNumber: Int,
    var busID: Int,
    val rowNumber: Int,
    val columnNumber: Int
)