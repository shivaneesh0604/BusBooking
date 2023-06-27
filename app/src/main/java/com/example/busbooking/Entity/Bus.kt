package com.example.busbooking.Entity

import com.example.busbooking.Enums.Amenities
import com.example.busbooking.Enums.BusType
import com.example.busbooking.Enums.SeatingType

data class Bus(
    val busID: Int,
    val travelsID: Int,
    val busNumber: String,
    var busRating: Float,
    var busAmenities: List<Amenities>,
    val busType: BusType,
    val seatingType: SeatingType
)