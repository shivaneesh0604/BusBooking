package com.example.busbooking.ViewModel

import androidx.lifecycle.ViewModel
import com.example.busbooking.DataClass.BookingsDataClass
import com.example.busbooking.Model.DataLayer.DataRetriever

class BookingsViewModel:ViewModel() {

    private var bookings : List<BookingsDataClass> = mutableListOf()

    fun getBookings():List<BookingsDataClass>{
        bookings= DataRetriever().getBookings()
        return bookings
    }
}