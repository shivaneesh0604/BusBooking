package com.example.busbooking.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.busbooking.model.SelectedBusRouteDetails
import com.example.busbooking.R

class SelectedBusRouteFragmentRecyclerView(
    private val context: Context, private val routeTrips: List<SelectedBusRouteDetails>, private val selectedBusClickListener: SelectedBusClickListener
) : RecyclerView.Adapter<SelectedBusRouteFragmentRecyclerView.SelectedBusRouteViewHolder>() {

    class SelectedBusRouteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val boardingTime: TextView = view.findViewById(R.id.boardingTime)
        val droppingTime: TextView = view.findViewById(R.id.droppingTime)
        val tripFare: TextView = view.findViewById(R.id.tripFare)
        val busName: TextView = view.findViewById(R.id.busName)
        val busRating: TextView = view.findViewById(R.id.busRating)
        val busType: TextView = view.findViewById(R.id.busTypeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedBusRouteViewHolder {
        return SelectedBusRouteViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.selected_bus_route_recycler_view_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return routeTrips.size
    }

    override fun onBindViewHolder(holder: SelectedBusRouteViewHolder, position: Int) {
        val item = routeTrips[position]

        val boardingPoint =  item.boardingPointTime+" - "
        val tripFare = "₹ "+item.ticketFare
        holder.boardingTime.text =boardingPoint
        holder.droppingTime.text = item.droppingPointTime
        holder.busName.text = item.busName
        holder.busType.text = item.busType.toString()
        holder.busRating.text = item.busRating.toString()
        holder.tripFare.text = tripFare
        holder.itemView.setOnClickListener {
            selectedBusClickListener.selectedBusRoute(item.tripID,item.seatingType.toString())
        }
    }

    interface SelectedBusClickListener{
        fun selectedBusRoute(selectedTripID:Int, seatingType: String?)
    }
}
