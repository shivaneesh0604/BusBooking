package com.example.busbooking.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busbooking.dataclass.BookingsDetails
import com.example.busbooking.model.datalayer.DataRetriever

class BookingsViewModel:ViewModel() {

    private var bookings : List<BookingsDetails> = mutableListOf()

    fun getBookings():List<BookingsDetails>{
        bookings= DataRetriever().getBookings()
        return bookings
    }
}