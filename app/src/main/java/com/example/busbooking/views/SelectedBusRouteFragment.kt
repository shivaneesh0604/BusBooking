package com.example.busbooking.views

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busbooking.model.SelectedRoutesAndDate
import com.example.busbooking.R
import com.example.busbooking.recyclerview.SelectedBusRouteFragmentRecyclerView
import com.example.busbooking.viewmodels.SelectedBusFragmentViewModel
import com.example.busbooking.databinding.FragmentSelectedBusRouteBinding


class SelectedBusRouteFragment : Fragment(),
    SelectedBusRouteFragmentRecyclerView.SelectedBusClickListener {

    private lateinit var selectedBusRouteBinding: FragmentSelectedBusRouteBinding
    private lateinit var selectedBusRouteViewModel: SelectedBusFragmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedBusRouteViewModel =
            ViewModelProvider(this)[SelectedBusFragmentViewModel::class.java]
        println("created selectedBusRoute")
    }

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
                selectedBusRouteViewModel.getSelectedBusRoutes(), this
            )

        selectedBusRouteBinding.selectedBusRouteRecyclerView.adapter = itemsAdapter


//        if (selectedBusRouteViewModel.selectedTripID != null) {
//            selectedBusRoute(
//                selectedBusRouteViewModel.selectedTripID!!,
//                selectedBusRouteViewModel.seatingType
//            )
//        }

        return selectedBusRouteBinding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        println("check selectedBusRouteViewModel ${selectedBusRouteViewModel.selectedTripID}")
//        if (savedInstanceState != null) {
//            println("check orientation")
//            selectedBusRoute(selectedBusRouteViewModel.selectedTripID!!,selectedBusRouteViewModel.seatingType)
//        }
//    }

    override fun selectedBusRoute(selectedTripID: Int, seatingType: String?) {
        println("addselectedTrip frag")
        val selectedTripFragment = SelectedTripFragment()
        selectedTripFragment.arguments = Bundle().apply {
            putInt("tripID", selectedTripID)
            putString("seatingType", seatingType.toString())
        }
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragmentTransaction.replace(
                R.id.nav_host_fragment,
                selectedTripFragment,
                "SelectedTripFragment"
            )
        } else {
            fragmentTransaction.replace(
                R.id.selectedTrip,
                selectedTripFragment,
                "SelectedTripFragment"
            )
        }
        fragmentTransaction.addToBackStack(null).commit()
        selectedBusRouteViewModel.selectedTripID = selectedTripID
        selectedBusRouteViewModel.seatingType = seatingType

    }

}