package com.example.busbooking.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busbooking.recyclerview.BookingFragmentRecyclerView
import com.example.busbooking.viewmodels.BookingsViewModel
import com.example.busbooking.databinding.FragmentBookingsBinding

class BookingsFragment : Fragment() {

    private lateinit var binding: FragmentBookingsBinding
    private val bookingsViewModel :BookingsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBookingsBinding.inflate(inflater, container, false)

        binding.bookingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemsAdapter = BookingFragmentRecyclerView(requireContext(),bookingsViewModel.getBookings() )

        binding.bookingRecyclerView.adapter = itemsAdapter

        return binding.root
    }
}