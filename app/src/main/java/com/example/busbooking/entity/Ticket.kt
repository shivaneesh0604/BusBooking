package com.example.busbooking.entity

import com.example.busbooking.enums.Areas
import com.example.busbooking.enums.BookingStatus

data class Ticket(
    val ticketID: Int,
    val tripID: Int,
    val seatNumber: Int,
    val ticketPrice: Int,
    var bookingStatus: BookingStatus,
    val passengerName: String,
    val passengerAge: Int,
    val bookedPassengerID: Int,
    val boardingPoint: Areas,
    val droppingPoint: Areas,
    val boardingTimeAndDate: String,
    val droppingTimeAndDate: String,
)
