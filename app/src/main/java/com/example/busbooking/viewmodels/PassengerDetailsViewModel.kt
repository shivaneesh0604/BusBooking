package com.example.busbooking.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busbooking.dataclass.PassengerDetails
import com.example.busbooking.model.datalayer.DataRetriever

class PassengerDetailsViewModel:ViewModel() {

    fun updateBooking(passengerDetails: List<PassengerDetails>, tripId:Int):Boolean{
        return DataRetriever().updateBooking(passengerDetails,tripId)
    }

}