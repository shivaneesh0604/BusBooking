package com.example.busbooking.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busbooking.dataclass.PassengerDetailsDataClass
import com.example.busbooking.recyclerviews.PassengerDetailsFragmentRecyclerView
import com.example.busbooking.viewmodels.PassengerDetailsViewModel
import com.example.busbooking.databinding.FragmentPassengerDetails2Binding

class PassengerDetailsFragment(
    private val passengerDetailsDataClass: List<PassengerDetailsDataClass>,
    private val tripId: Int
) :
    Fragment() {
    private lateinit var binding: FragmentPassengerDetails2Binding
    private val passengerDetailsViewModel: PassengerDetailsViewModel by viewModels()
    private var toast: Toast? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPassengerDetails2Binding.inflate(inflater, container, false)

        binding.passengerDetailsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        val itemsAdapter =
            PassengerDetailsFragmentRecyclerView(requireContext(), passengerDetailsDataClass)

        binding.passengerDetailsRecyclerView.adapter = itemsAdapter

        val passengerDatas = itemsAdapter.getPassengerDetails()

        binding.bookSeatButton.setOnClickListener {

            var check = true
            for (passengerDetails in passengerDatas) {
                if (passengerDetails.passengerName.isNotEmpty() && passengerDetails.passengerAge.isNotEmpty() && passengerDetails.gender.isNotEmpty()) {
                    check = true
                } else {
                    check = false
                    break
                }
            }
            if (!check) {
                if (toast != null) {
                    toast!!.cancel()
                }
                Toast.makeText(requireContext(), "Fill all data's", Toast.LENGTH_SHORT).show()
            } else {
                val ticketConfirmation =
                    passengerDetailsViewModel.updateBooking(passengerDetailsDataClass, tripId)

                if (ticketConfirmation) {
                    val alertDialog = AlertDialog.Builder(requireContext())
                        .setTitle("Success")
                        .setMessage("Booked Successfully")
                        .setPositiveButton("OK", null)
                        .create()

                    alertDialog.show()
                    requireActivity().supportFragmentManager.popBackStack()
                    requireActivity().supportFragmentManager.popBackStack()
                    requireActivity().supportFragmentManager.popBackStack()

                }
            }
        }


        return binding.root
    }
}