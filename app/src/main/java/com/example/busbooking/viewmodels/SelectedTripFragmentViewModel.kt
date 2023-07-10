package com.example.busbooking.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busbooking.model.TripDetails
import com.example.busbooking.database.datalayer.DataRetriever

class SelectedTripFragmentViewModel:ViewModel() {

    private val dataRetriever = DataRetriever()
    private val selectedSeats:MutableList<Int> = mutableListOf()

    fun getBookedSeats(tripId:Int):List<Int>{
        return dataRetriever.retrieveBookedSeats(tripId)
    }

    fun getTripDetails(tripId: Int):TripDetails{
        return dataRetriever.getTripDetails(tripId)

    }
    fun getSeatPrice(tripId: Int):Int?{
        return dataRetriever.retrieveSeatPrice(tripId)
    }

    fun getSeatsList(tripId: Int):List<Int>{
        return dataRetriever.retrieveSeats(tripId)
    }

    fun addSelectedSeats(seatNumber : Int){
        selectedSeats.add(seatNumber)
    }

    fun removeSelectedSeats(seatNumber: Int){
        selectedSeats.remove(seatNumber)
    }

    fun getSelectedSeats():MutableList<Int>{
        return selectedSeats
    }
}