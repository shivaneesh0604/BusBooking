package com.example.busbooking.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busbooking.dataclass.TripDetails
import com.example.busbooking.model.datalayer.DataRetriever

class SelectedTripFragmentViewModel:ViewModel() {

    private var bookedSeats : List<Int> = mutableListOf()
    private var seatPrice :Int? = null
    private lateinit var tripDetails :TripDetails
    private var seatsList = listOf<Int>()
    private val dataRetriever = DataRetriever()

    fun getBookedSeats(tripId:Int):List<Int>{
        bookedSeats = dataRetriever.retrieveBookedSeats(tripId)
        return bookedSeats
    }

    fun getTripDetails(tripId: Int):TripDetails{
        tripDetails = dataRetriever.getTripDetails(tripId)
        return tripDetails
    }
    fun getSeatPrice(tripId: Int):Int?{
        seatPrice = dataRetriever.retrieveSeatPrice(tripId)
        return seatPrice
    }

    fun getSeatsList(tripId: Int):List<Int>{
        seatsList = dataRetriever.retrieveSeats(tripId)
        return seatsList
    }
}