package com.example.busbooking.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busbooking.model.SelectedBusRouteDetails
import com.example.busbooking.model.SelectedRoutesAndDate
import com.example.busbooking.database.datalayer.DataRetriever
import com.example.busbooking.enums.SeatingType

class SelectedBusFragmentViewModel : ViewModel() {

    private var selectedBusRouteDetails: List<SelectedBusRouteDetails> = listOf()

    lateinit var selectedRoutesAndDate: SelectedRoutesAndDate

    var selectedTripID:Int?=null
    var seatingType:String?=null

    fun getSelectedBusRoutes(): List<SelectedBusRouteDetails> {
        selectedBusRouteDetails =
            DataRetriever().retrieveSelectedBusData(selectedRoutesAndDate)
        return selectedBusRouteDetails
    }

}