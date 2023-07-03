package com.example.busbooking.entity

import com.example.busbooking.enums.Amenities
import com.example.busbooking.enums.BusType
import com.example.busbooking.enums.SeatingType

data class Bus(
    val busID: Int,
    val travelsID: Int,
    val busNumber: String,
    var busRating: Float,
    var busAmenities: List<Amenities>,
    val busType: BusType,
    val seatingType: SeatingType
)