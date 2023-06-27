package com.example.busbooking.ViewModel

import androidx.lifecycle.ViewModel
import com.example.busbooking.DataClass.SelectedBusRouteDataClass
import com.example.busbooking.DataClass.SelectedRoutesAndDate
import com.example.busbooking.Model.DataLayer.DataRetriever

class SelectedBusFragmentViewModel : ViewModel() {

    private var selectedBusRouteDataClass: MutableList<SelectedBusRouteDataClass> = mutableListOf()

    lateinit var selectedRoutesAndDate: SelectedRoutesAndDate

    fun getSelectedBusRouteDataClass(): List<SelectedBusRouteDataClass> {
        selectedBusRouteDataClass =
            DataRetriever().retrieveSelectedBusData(selectedRoutesAndDate)
        return selectedBusRouteDataClass
    }

}