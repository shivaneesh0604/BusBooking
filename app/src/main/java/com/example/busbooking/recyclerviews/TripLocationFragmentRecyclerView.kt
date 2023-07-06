package com.example.busbooking.recyclerviews

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.busbooking.enums.Areas
import com.example.busbooking.enums.TripLocation
import com.example.busbooking.R

class TripLocationFragmentRecyclerView(
    private val context: Context,
    private var tripLocations: List<Areas>,
    private val selectedTripLocation: TripLocationClickListener,
    private val tripLocation: TripLocation
) : RecyclerView.Adapter<TripLocationFragmentRecyclerView.TripLocationViewHolder>() {

    class TripLocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tripLocationName: TextView = view.findViewById(R.id.tripLocationAreaName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripLocationViewHolder {
        return TripLocationViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.trip_location_recyclerview_items, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return tripLocations.size
    }

    override fun onBindViewHolder(holder: TripLocationViewHolder, position: Int) {
        val item = tripLocations[position]

        holder.tripLocationName.text = item.toString()

        holder.itemView.setOnClickListener {
            Log.e("onclick", "cameTripLocation in TripLocation Class")
            selectedTripLocation.selectedTripLocation(item.toString(),tripLocation)
        }

    }

    fun updateList(tripLocations: List<Areas>){
        this.tripLocations = tripLocations
    }

    interface TripLocationClickListener{
        fun selectedTripLocation(selectedtripLocation: String,tripLocation: TripLocation)
    }
}