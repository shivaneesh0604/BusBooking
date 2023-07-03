package com.example.busbooking.model.database

import com.example.busbooking.dataclass.PassengerDetailsDataClass
import com.example.busbooking.dataclass.SelectedRoutesAndDate
import com.example.busbooking.entity.*
import com.example.busbooking.enums.BookingStatus

internal object Database {
    private val travels: List<Travels> = DataProvider.addTravels()

    private val buses: List<Bus> = DataProvider.addBus()

    private val seats: List<Seat> = DataProvider.addSeat()

    private val drivers: List<Driver> = DataProvider.addDrivers()

    private val passenger: Passenger = Passenger(1, "Shivaneesh", 1234567890, 22)

    private val tickets: MutableList<Ticket> = mutableListOf()

    private val trips: List<Trip> = DataProvider.addTrips()

    private var ticketID = 1

    fun getTripForSelectedRouteAndDate(selectedRoutesAndDate: SelectedRoutesAndDate): List<Trip> {
        return trips.filter { it.boardingArea.toString() == selectedRoutesAndDate.selectedBoardingPoint && it.droppingArea.toString() == selectedRoutesAndDate.selectedDroppingPoint && it.travelDateFrom == selectedRoutesAndDate.selectedDate }
            .toList()
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

    fun updateBooking(passengerDetails: List<PassengerDetailsDataClass>, tripId: Int):Boolean {
        val trip = getTrip(tripId)
        for (details in passengerDetails) {
            val ticket = Ticket(
                ticketID,
                trip!!.tripID,
                details.seatNumber,
                trip.perSeatPrice,
                BookingStatus.Booked,
                details.passengerName,
                details.passengerAge.toInt(),
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
        return tickets.toList()
    }


}