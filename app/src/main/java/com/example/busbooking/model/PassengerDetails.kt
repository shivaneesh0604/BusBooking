package com.example.busbooking.model

import android.os.Parcel
import android.os.Parcelable

data class PassengerDetails(var passengerName: String?, var gender: String?, var passengerAge: String?, val seatNumber: Int):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(passengerName)
        parcel.writeString(gender)
        parcel.writeString(passengerAge)
        parcel.writeInt(seatNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PassengerDetails> {
        override fun createFromParcel(parcel: Parcel): PassengerDetails {
            return PassengerDetails(parcel)
        }

        override fun newArray(size: Int): Array<PassengerDetails?> {
            return arrayOfNulls(size)
        }
    }
}