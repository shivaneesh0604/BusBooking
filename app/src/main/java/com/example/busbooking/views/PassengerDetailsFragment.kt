package com.example.busbooking.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busbooking.model.PassengerDetails
import com.example.busbooking.recyclerview.PassengerDetailsFragmentRecyclerView
import com.example.busbooking.viewmodels.PassengerDetailsViewModel
import com.example.busbooking.databinding.FragmentPassengerDetailsBinding

class PassengerDetailsFragment :
    Fragment() {
    private lateinit var binding: FragmentPassengerDetailsBinding
    private val passengerDetailsViewModel: PassengerDetailsViewModel by viewModels()
    private var toast: Toast? = null
    private lateinit var passengerDetails: ArrayList<PassengerDetails>
    private var tripId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tripId = it.getInt("tripId")
            passengerDetails =
                it.getParcelableArrayList<PassengerDetails>("passengerDetails") as ArrayList<PassengerDetails>
        }

        if (savedInstanceState != null) {
            passengerDetails =
                savedInstanceState.getParcelableArrayList<PassengerDetails>("passengerDetails") as ArrayList<PassengerDetails>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPassengerDetailsBinding.inflate(inflater, container, false)

        binding.passengerDetailsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        Log.e("checkPassengerDetails","checkPassengerDetails $passengerDetails")
        val itemsAdapter =
            PassengerDetailsFragmentRecyclerView(requireContext(), passengerDetails)

        binding.passengerDetailsRecyclerView.adapter = itemsAdapter

        val passengerData = itemsAdapter.getPassengerDetails()

        binding.bookSeatButton.setOnClickListener {

            var check = true
            for (passengerDetails in passengerData) {
                if (passengerDetails.passengerName?.isNotEmpty() == true && passengerDetails.passengerAge?.isNotEmpty() == true && passengerDetails.gender?.isNotEmpty() == true) {
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
                    passengerDetailsViewModel.updateBooking(passengerDetails, tripId)

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("passengerDetails", passengerDetails)
    }
}