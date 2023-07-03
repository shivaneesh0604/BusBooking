package com.example.busbooking.recyclerviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.busbooking.dataclass.BookingsDataClass
import com.example.busbooking.R

class BookingFragmentRecyclerView(private val context: Context,private val bookings: List<BookingsDataClass>):
    RecyclerView.Adapter<BookingFragmentRecyclerView.BookingsFragmentViewHolder>() {

    class BookingsFragmentViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.tripDate)
        val busRoute: TextView = view.findViewById(R.id.busRouteRecyclerView)
        val travelsName: TextView = view.findViewById(R.id.travelsNameRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingsFragmentViewHolder {
        return BookingsFragmentViewHolder(
            LayoutInflater.from(context)
            .inflate(R.layout.bookings_fragment_recycler_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return bookings.size
    }

    override fun onBindViewHolder(holder: BookingsFragmentViewHolder, position: Int) {
        val item = bookings[position]

        holder.date.text =  item.date
        holder.busRoute.text = item.busRoute
        holder.travelsName.text = item.travelsName

    }
}