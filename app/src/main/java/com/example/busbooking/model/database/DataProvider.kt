package com.example.busbooking.model.database

import android.annotation.SuppressLint
import com.example.busbooking.entity.*
import com.example.busbooking.enums.Amenities
import com.example.busbooking.enums.Areas
import com.example.busbooking.enums.BusType
import com.example.busbooking.enums.SeatingType
import java.text.SimpleDateFormat
import java.util.*

object DataProvider {
    fun addSeat(): MutableList<Seat> {
        return mutableListOf(
            Seat(1, 1, 1, 1),
            Seat(2, 1, 1, 2),
            Seat(3, 1, 1, 3),
            Seat(4, 1, 1, 4),
            Seat(5, 1, 2, 1),
            Seat(6, 1, 2, 2),
            Seat(7, 1, 2, 3),
            Seat(8, 1, 2, 4),
            Seat(9, 1, 3, 1),
            Seat(10, 1, 3, 2),
            Seat(11, 1, 3, 3),
            Seat(12, 1, 3, 4),
            Seat(13, 1, 4, 1),
            Seat(14, 1, 4, 2),
            Seat(15, 1, 4, 3),
            Seat(16, 1, 4, 4),
            Seat(17, 1, 5, 1),
            Seat(18, 1, 5, 2),
            Seat(19, 1, 5, 3),
            Seat(20, 1, 5, 4),
            Seat(21, 1, 6, 1),
            Seat(22, 1, 6, 2),
            Seat(23, 1, 6, 3),//semi sleeper

            Seat(1, 3, 1, 1),
            Seat(2, 3, 1, 2),
            Seat(3, 3, 1, 3),
            Seat(4, 3, 1, 4),
            Seat(5, 3, 2, 1),
            Seat(6, 3, 2, 2),
            Seat(7, 3, 2, 3),
            Seat(8, 3, 2, 4),
            Seat(9, 3, 3, 1),
            Seat(10, 3, 3, 2),
            Seat(11, 3, 3, 3),
            Seat(12, 3, 3, 4),
            Seat(13, 3, 4, 1),
            Seat(14, 3, 4, 2),
            Seat(15, 3, 4, 3),
            Seat(16, 3, 4, 4),
            Seat(17, 3, 5, 1),
            Seat(18, 3, 5, 2),
            Seat(19, 3, 5, 3),
            Seat(20, 3, 5, 4),
            Seat(21, 3, 6, 1),
            Seat(22, 3, 6, 2),
            Seat(23, 3, 6, 3),//semi sleeper(24seats)

            Seat(1, 2, 1, 1),
            Seat(2, 2, 1, 2),
            Seat(3, 2, 1, 3),
            Seat(4, 2, 2, 1),
            Seat(5, 2, 2, 2),
            Seat(6, 2, 2, 3),
            Seat(7, 2, 3, 1),
            Seat(8, 2, 3, 2),
            Seat(9, 2, 3, 3),
            Seat(10, 2, 4, 1),
            Seat(11, 2, 4, 2),
            Seat(12, 2, 4, 3),
            Seat(13, 2, 5, 1),
            Seat(14, 2, 5, 2),
            Seat(15, 2, 5, 3),
            Seat(16, 2, 6, 1),
            Seat(17, 2, 6, 2),
            Seat(18, 2, 6, 3),//sleeper (18 seats)
        )
    }

    fun addBus(): MutableList<Bus> {
        return mutableListOf(
            Bus(
                busID = 1,
                travelsID = 1,
                busNumber = "TN 21 N 2000",
                busRating = 4.5f,
                busAmenities = listOf(
                    Amenities.TrackMyBus,
                    Amenities.ChargingPoint,
                    Amenities.BedSheet,
                    Amenities.Wifi,
                    Amenities.EmergencyContactNumber
                ),
                busType = BusType.AC,
                seatingType = SeatingType.SemiSleeper
            ),
            Bus(
                busID = 2,
                travelsID = 2,
                busNumber = "TN 21 N 2001",
                busRating = 4.2f,
                busAmenities = listOf(
                    Amenities.TrackMyBus,
                    Amenities.ChargingPoint,
                    Amenities.Wifi
                ),
                busType = BusType.AC,
                seatingType = SeatingType.Sleeper
            ),
            Bus(
                busID = 3,
                travelsID = 1,
                busNumber = "TN 21 N 2002",
                busRating = 4.7f,
                busAmenities = listOf(
                    Amenities.TrackMyBus,
                    Amenities.ChargingPoint,
                    Amenities.BedSheet,
                    Amenities.Wifi,
                    Amenities.EmergencyContactNumber
                ),
                busType = BusType.AC,
                seatingType = SeatingType.SemiSleeper
            )
        )
    }

    fun addTravels(): MutableList<Travels> {
        return mutableListOf(
            Travels(1, "SRS Travels", 1234567890),
            Travels(2, "MRM Travels", 9876543210)
        )
    }

    fun addDrivers(): MutableList<Driver> {
        return mutableListOf(
            Driver(
                driverID = 1,
                travelsID = 1,
                driverName = "John Doe",
                driverContactNumber = 1234567890
            ),
            Driver(
                driverID = 2,
                travelsID = 2,
                driverName = "Jane Smith",
                driverContactNumber = 9876543210
            ),
            Driver(
                driverID = 3,
                travelsID = 1,
                driverName = "Mike Johnson",
                driverContactNumber = 4567891230
            )
        )
    }

    @SuppressLint("SimpleDateFormat")
    fun addTrips(): MutableList<Trip> {
        val currentDateAndTime = Calendar.getInstance()
        currentDateAndTime.time = Date()

        val dateFormat = SimpleDateFormat("E, dd MMM")

        val currentDay: String = dateFormat.format(currentDateAndTime.time)

        val nextDate = Calendar.getInstance()
        nextDate.time = Date()

        nextDate.add(Calendar.DAY_OF_YEAR, 1)

        val nextDayString :String = dateFormat.format(nextDate.time)


        return mutableListOf(
            Trip(
                tripID = 1,
                busID = 1,
                driverID = 1,
                boardingArea = Areas.Chennai,
                droppingArea = Areas.Nagercoil,
                travelDateFrom = currentDay,
                travelDateTo = nextDayString,
                perSeatPrice = 650,
                boardingTime = "17:50",
                droppingTime = "08:30"
            ),
            Trip(
                tripID = 2,
                busID = 2,
                driverID = 2,
                boardingArea = Areas.Chennai,
                droppingArea = Areas.Madurai,
                travelDateFrom = currentDay,
                travelDateTo = nextDayString,
                perSeatPrice = 600,
                boardingTime = "19:50",
                droppingTime = "06:30"
            ),
            Trip(
                tripID = 3,
                busID = 3,
                driverID = 1,
                boardingArea = Areas.Chennai,
                droppingArea = Areas.Kochin,
                travelDateFrom = currentDay,
                travelDateTo = nextDayString,
                perSeatPrice = 550,
                boardingTime = "19:50",
                droppingTime = "07:30"
            ),
            Trip(
                tripID = 4,
                busID = 3,
                driverID = 1,
                boardingArea = Areas.Nagercoil,
                droppingArea = Areas.Kochin,
                travelDateFrom = currentDay,
                travelDateTo = nextDayString,
                perSeatPrice = 700,
                boardingTime = "20:50",
                droppingTime = "05:30"
            ),
            Trip(
                tripID = 5,
                busID = 3,
                driverID = 1,
                boardingArea = Areas.Nagercoil,
                droppingArea = Areas.Kochin,
                travelDateFrom = currentDay,
                travelDateTo = nextDayString,
                perSeatPrice = 600,
                boardingTime = "18:50",
                droppingTime = "03:30"
            )
        )
    }
}