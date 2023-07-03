package com.example.busbooking.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busbooking.dataclass.PassengerDetailsDataClass
import com.example.busbooking.model.datalayer.DataRetriever

class PassengerDetailsViewModel:ViewModel() {

    fun updateBooking( passengerDetailsDataClass: List<PassengerDetailsDataClass>,  tripId:Int):Boolean{
        return DataRetriever().updateBooking(passengerDetailsDataClass,tripId)
    }

}