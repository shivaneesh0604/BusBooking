package com.example.busbooking.ViewModel

import androidx.lifecycle.ViewModel
import com.example.busbooking.DataClass.TripDetails
import com.example.busbooking.Model.DataLayer.DataRetriever

class SelectedTripFragmentViewModel:ViewModel() {

    private var bookedSeats : List<Int> = mutableListOf()
    private var seatPrice :Int? = null
    private lateinit var tripDetails :TripDetails
    private var seatsList = listOf<Int>()

    fun getBookedSeats(tripId:Int):List<Int>{
        bookedSeats = DataRetriever().retrieveBookedSeats(tripId)
        return bookedSeats
    }

    fun getTripDetails(tripId: Int):TripDetails{
        tripDetails = DataRetriever().getTripDetails(tripId)
        return tripDetails
    }
    fun getSeatPrice(tripId: Int):Int?{
        seatPrice = DataRetriever().retrieveSeatPrice(tripId)
        return seatPrice
    }

    fun getSeatsList(tripId: Int):List<Int>{
        seatsList = DataRetriever().retrieveSeats(tripId)
        return seatsList
    }
}