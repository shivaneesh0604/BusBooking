package com.example.busbooking.entity

data class Seat(
    val seatNumber: Int,
    var busID: Int,
    val rowNumber: Int,
    val columnNumber: Int
)