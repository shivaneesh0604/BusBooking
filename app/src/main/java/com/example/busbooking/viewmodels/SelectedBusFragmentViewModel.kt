package com.example.busbooking.viewmodels

import androidx.lifecycle.ViewModel
import com.example.busbooking.dataclass.SelectedBusRouteDataClass
import com.example.busbooking.dataclass.SelectedRoutesAndDate
import com.example.busbooking.model.datalayer.DataRetriever

class SelectedBusFragmentViewModel : ViewModel() {

    private var selectedBusRouteDataClass: MutableList<SelectedBusRouteDataClass> = mutableListOf()

    lateinit var selectedRoutesAndDate: SelectedRoutesAndDate

    fun getSelectedBusRoutes(): List<SelectedBusRouteDataClass> {
        selectedBusRouteDataClass =
            DataRetriever().retrieveSelectedBusData(selectedRoutesAndDate)
        return selectedBusRouteDataClass
    }

}