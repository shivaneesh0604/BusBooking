package com.example.busbooking.DataClass

import com.example.busbooking.Enums.BusType
import com.example.busbooking.Enums.SeatingType

data class SelectedBusRouteDataClass(
    val tripID:Int,
    val boardingPointTime: String,
    val droppingPointTime: String,
    val ticketFare: String,
    val busName: String,
    val busType: BusType,
    val busRating: Float,
    val seatingType: SeatingType
)