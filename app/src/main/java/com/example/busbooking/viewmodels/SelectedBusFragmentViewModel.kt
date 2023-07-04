package com.example.busbooking.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busbooking.dataclass.SelectedBusRouteDetails
import com.example.busbooking.dataclass.SelectedRoutesAndDate
import com.example.busbooking.model.datalayer.DataRetriever

class SelectedBusFragmentViewModel : ViewModel() {

    private var selectedBusRouteDetails: List<SelectedBusRouteDetails> = listOf()

    lateinit var selectedRoutesAndDate: SelectedRoutesAndDate

    fun getSelectedBusRoutes(): List<SelectedBusRouteDetails> {
        selectedBusRouteDetails =
            DataRetriever().retrieveSelectedBusData(selectedRoutesAndDate)
        return selectedBusRouteDetails
    }

}