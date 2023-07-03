package com.example.busbooking.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busbooking.dataclass.BookingsDataClass
import com.example.busbooking.model.datalayer.DataRetriever

class BookingsViewModel:ViewModel() {

    private var bookings : List<BookingsDataClass> = mutableListOf()

    fun getBookings():List<BookingsDataClass>{
        bookings= DataRetriever().getBookings()
        return bookings
    }
}