package com.example.busbooking.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.busbooking.model.BookingsDetails
import com.example.busbooking.R
import com.example.busbooking.diffutil.BookingsRecyclerViewDiffUtil

class BookingFragmentRecyclerView():
    RecyclerView.Adapter<BookingFragmentRecyclerView.BookingsFragmentViewHolder>() {

    private var bookings: List<BookingsDetails> = emptyList()
    class BookingsFragmentViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.tripDate)
        val busRoute: TextView = view.findViewById(R.id.busRouteRecyclerView)
        val travelsName: TextView = view.findViewById(R.id.travelsNameRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingsFragmentViewHolder {
        return BookingsFragmentViewHolder(
            LayoutInflater.from(parent.context)
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

    fun setData(newList: List<BookingsDetails>){
        Log.e("checkBookings","checkBookings in setData of bookingsFragRecyclerView $newList")
        val diffUtil = BookingsRecyclerViewDiffUtil(bookings,newList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        bookings = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}