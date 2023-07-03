package com.example.busbooking.Model.DataLayer

import com.example.busbooking.DataClass.*
import com.example.busbooking.Entity.Ticket
import com.example.busbooking.Entity.Trip
import com.example.busbooking.Model.Database.Database

class DataRetriever {

    fun retrieveSelectedBusData(selectedRoutesAndDate: SelectedRoutesAndDate): MutableList<SelectedBusRouteDataClass> {
        val selectedBusData: MutableList<SelectedBusRouteDataClass> = mutableListOf()
        val trips: MutableList<Trip> =
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

        return selectedBusData

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

    fun getBookings(): MutableList<BookingsDataClass> {
        val bookings = Database.getBookings()
        val bookingsDatas = mutableMapOf<Int,BookingsDataClass>()
        for (i in bookings) {
            val trip = Database.getTrip(i.tripID)
            val bus = Database.getBus(trip!!.busID)
            val travelsName = Database.getTravelsName(bus!!.travelsID)
            bookingsDatas[i.ticketID] = BookingsDataClass(
                i.boardingTimeAndDate+"-" + i.droppingTimeAndDate,
                i.boardingPoint.toString()+"-" + i.droppingPoint.toString(),travelsName!!
            )
        }
        return bookingsDatas.values.toMutableList()
    }
}