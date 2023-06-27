package com.example.busbooking.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busbooking.DataClass.SelectedRoutesAndDate
import com.example.busbooking.Enums.SeatingType
import com.example.busbooking.R
import com.example.busbooking.RecyclerView.SelectedBusRouteFragmentRecyclerView
import com.example.busbooking.ViewModel.SelectedBusFragmentViewModel
import com.example.busbooking.databinding.FragmentSelectedBusRouteBinding


class SelectedBusRouteFragment : Fragment(), SelectedBusRouteFragmentRecyclerView.SelectedBusClickListener {

    private lateinit var selectedBusRouteBinding: FragmentSelectedBusRouteBinding
    private val selectedBusRouteViewModel: SelectedBusFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        selectedBusRouteBinding =
            FragmentSelectedBusRouteBinding.inflate(inflater, container, false)

        val result = arguments

        val selectedRoutesAndDate = SelectedRoutesAndDate(
            result?.getString("boardingLocation")!!,
            result.getString("droppingLocation")!!,
            result.getString("selectedDate")!!
        )

        selectedBusRouteViewModel.selectedRoutesAndDate = selectedRoutesAndDate

        selectedBusRouteBinding.selectedBusRouteRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        val itemsAdapter =
            SelectedBusRouteFragmentRecyclerView(
                requireContext(),
                selectedBusRouteViewModel.getSelectedBusRouteDataClass(), this
            )

        selectedBusRouteBinding.selectedBusRouteRecyclerView.adapter = itemsAdapter



        return selectedBusRouteBinding.root
    }

    override fun selectedBusRoute(selectedTripID: Int,seatingType: SeatingType) {
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.nav_host_fragment,
            SelectedTripFragment(selectedTripID,seatingType),
            "SelectedTripFragment"
        ).addToBackStack(null).commit()
    }

}