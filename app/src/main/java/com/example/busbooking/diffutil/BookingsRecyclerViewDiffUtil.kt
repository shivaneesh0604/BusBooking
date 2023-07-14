package com.example.busbooking.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.busbooking.model.BookingsDetails

class BookingsRecyclerViewDiffUtil(private val oldList:List<BookingsDetails>, private val newList: List<BookingsDetails>):DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].ticketID == newList[newItemPosition].ticketID
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].busRoute != newList[newItemPosition].busRoute -> {
                false
            }
            oldList[oldItemPosition].travelsName != newList[newItemPosition].travelsName-> {
                false
            }
            oldList[oldItemPosition].date != newList[newItemPosition].date ->{
                false
            }
            else -> true
        }

    }
}