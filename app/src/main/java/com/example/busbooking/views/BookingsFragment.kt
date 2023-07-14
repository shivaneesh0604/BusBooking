package com.example.busbooking.views

import android.os.Bundle
import android.util.Log
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
    private val bookingsRecyclerView = BookingFragmentRecyclerView()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("checkBookings","checkBookings in Bookings Frag")
        // Inflate the layout for this fragment
        binding = FragmentBookingsBinding.inflate(inflater, container, false)

        binding.bookingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.bookingRecyclerView.adapter = bookingsRecyclerView

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        bookingsRecyclerView.setData(bookingsViewModel.getBookings())
    }
}