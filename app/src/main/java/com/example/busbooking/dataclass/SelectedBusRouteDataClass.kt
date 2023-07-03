package com.example.busbooking.dataclass

import com.example.busbooking.enums.BusType
import com.example.busbooking.enums.SeatingType

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