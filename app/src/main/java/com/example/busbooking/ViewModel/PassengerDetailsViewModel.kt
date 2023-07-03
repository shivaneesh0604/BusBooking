package com.example.busbooking.ViewModel

import androidx.lifecycle.ViewModel
import com.example.busbooking.DataClass.PassengerDetailsDataClass
import com.example.busbooking.Model.DataLayer.DataRetriever

class PassengerDetailsViewModel:ViewModel() {

    fun updateBooking( passengerDetailsDataClass: List<PassengerDetailsDataClass>,  tripId:Int):Boolean{
        return DataRetriever().updateBooking(passengerDetailsDataClass,tripId)
    }

}