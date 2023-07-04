package com.example.busbooking.model.datalayer

import com.example.busbooking.dataclass.*
import com.example.busbooking.entity.Ticket
import com.example.busbooking.entity.Trip
import com.example.busbooking.model.database.Database

class DataRetriever {

    fun retrieveSelectedBusData(selectedRoutesAndDate: SelectedRoutesAndDate): List<SelectedBusRouteDataClass> {
        val selectedBusData: MutableList<SelectedBusRouteDataClass> = mutableListOf()
        val trips: List<Trip> =
            Database.getTripForSelectedRouteAndDate(selectedRoutesAndDate)
        for (trip in trips) {
            val bus = Database.getBus(trip.busID)
            val travels = Database.getTravelsName(bus!!.travelsID)
            selectedBusData.add(
                SelectedBusRouteDataClass(
                    trip.tripID,
                    trip.boardingArea.toString(),
                    trip.droppingArea.toString(),
                    trip.perSeatPrice.toString(),
                    travels!!,
                    bus.busType,
                    bus.busRating,
                    bus.seatingType
                )
            )
        }

        return selectedBusData.toList()

    }

    fun retrieveBookedSeats(tripId: Int): List<Int> {
        val bookedSeats = mutableListOf<Int>()
        val trips: List<Ticket> = Database.getBookedTickets(tripId)
        if (trips.isNotEmpty()) {
            for (trip in trips) {
                bookedSeats.add(trip.seatNumber)
            }
        }

        return bookedSeats
    }

    fun retrieveSeats(tripId: Int):List<Int>{
        val trip = Database.getTrip(tripId)
        return Database.getSeatsList(trip!!.busID)
    }

    fun getTripDetails(tripId: Int): TripDetails {
        val trip = Database.getTrip(tripId)

        val bus = Database.getBus(trip!!.busID)

        val travelsName = Database.getTravelsName(bus!!.travelsID)

        return TripDetails(
            boardingArea = trip.boardingArea,
            droppingArea = trip.droppingArea,
            travelsName = travelsName!!,
            busType = bus.busType,
            trip.perSeatPrice,
            bus.busRating,
            trip.travelDateFrom,
            trip.boardingTime,
            trip.droppingTime
        )
    }

    fun retrieveSeatPrice(tripId: Int): Int? {
        return Database.getSeatPrice(tripId)
    }

    fun updateBooking(passengerDetailsDataClass: List<PassengerDetailsDataClass>, tripId: Int):Boolean {
        return Database.updateBooking(passengerDetailsDataClass, tripId)
    }

    fun getBookings(): List<BookingsDataClass> {
        val bookings = Database.getBookings()
        val bookingsData = mutableMapOf<Int,BookingsDataClass>()
        for (booking in bookings) {
            val trip = Database.getTrip(booking.tripID)
            val bus = Database.getBus(trip!!.busID)
            val travelsName = Database.getTravelsName(bus!!.travelsID)
            bookingsData[booking.ticketID] = BookingsDataClass(
                booking.boardingTimeAndDate+"-" + booking.droppingTimeAndDate,
                booking.boardingPoint.toString()+"-" + booking.droppingPoint.toString(),travelsName!!
            )
        }
        return bookingsData.values.toList()
    }
}