package com.example.busbooking.Model.Database

import com.example.busbooking.DataClass.PassengerDetailsDataClass
import com.example.busbooking.DataClass.SelectedRoutesAndDate
import com.example.busbooking.DataClass.TripDetails
import com.example.busbooking.Entity.*
import com.example.busbooking.Enums.BookingStatus

internal object Database {
    private val travels: List<Travels> = DataProvider.addTravels()

    private val buses: List<Bus> = DataProvider.addBus()

    private val seats: List<Seat> = DataProvider.addSeat()

    private val drivers: List<Driver> = DataProvider.addDrivers()

    private val passenger: Passenger = Passenger(1, "Shivaneesh", 1234567890, 22)

    private val tickets: MutableList<Ticket> = mutableListOf()

    private val trips: List<Trip> = DataProvider.addTrips()

    private var ticketID = 1

    fun getTripForSelectedRouteAndDate(selectedRoutesAndDate: SelectedRoutesAndDate): MutableList<Trip> {
        return trips.filter { it.boardingArea.toString() == selectedRoutesAndDate.selectedBoardingPoint && it.droppingArea.toString() == selectedRoutesAndDate.selectedDroppingPoint && it.travelDateFrom == selectedRoutesAndDate.selectedDate }
            .toMutableList()
    }

    fun getBus(busID: Int): Bus? {

        return buses.find { it.busID == busID }

    }

    fun getSeatsList(busID: Int):List<Int>{
        val seatsList = mutableListOf<Int>()
        for (seat in seats){
            if (seat.busID==busID){
                seatsList.add(seat.seatNumber)
            }
        }

        return seatsList.toList()
    }

    fun getTravelsName(travelsID: Int): String? {

        val travels = travels.find { it.travelsID == travelsID }

        return travels?.travelsName

    }

    fun getBookedTickets(tripId: Int): List<Ticket> {
        return tickets.filter { it.tripID == tripId }.toList()
    }

    fun getSeatPrice(tripId: Int): Int? {
        val trip = trips.find { it.tripID == tripId }

        return trip?.perSeatPrice
    }

    fun getTrip(tripId: Int): Trip? {
        return trips.find { it.tripID == tripId }
    }

    fun updateBooking(passengerDetailsDataClass: List<PassengerDetailsDataClass>, tripId: Int):Boolean {
        val trip = getTrip(tripId)
        for (passengerDetails in passengerDetailsDataClass) {
            val ticket: Ticket = Ticket(
                ticketID,
                trip!!.tripID,
                passengerDetails.seatNumber,
                trip.perSeatPrice,
                BookingStatus.Booked,
                passengerDetails.passengerName,
                passengerDetails.passengerAge.toInt(),
                passenger.passengerID,
                trip.boardingArea,
                trip.droppingArea,
                trip.boardingTime,
                trip.droppingTime
            )

            tickets.add(ticket)
        }

        ticketID++

        return true
    }

    fun getBookings():List<Ticket>{
        return tickets.toMutableList()
    }


}