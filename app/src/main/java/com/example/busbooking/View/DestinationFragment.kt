package com.example.busbooking.View

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busbooking.Enums.Areas
import com.example.busbooking.Enums.TripLocation
import com.example.busbooking.R
import com.example.busbooking.RecyclerView.TripLocationFragmentRecyclerView
import com.example.busbooking.databinding.FragmentDestinationBinding
import java.util.*

class DestinationFragment(private val tripClickListener: TripLocationFragmentRecyclerView.TripClickListener)  : Fragment() {

    private lateinit var destBinding: FragmentDestinationBinding
    private lateinit var mMenuProvider: MenuProvider
    private val areasList: MutableList<Areas> = mutableListOf()
    private lateinit var recyclerViewItemsAdapter:TripLocationFragmentRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        for (i in Areas.values()) {
            areasList.add(i)
        }

        destBinding = FragmentDestinationBinding.inflate(inflater, container, false)

        destBinding.destRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewItemsAdapter =
            TripLocationFragmentRecyclerView(
                requireContext(),
                areasList as List<Areas>,
                tripClickListener,
                TripLocation.Destination
            )

        destBinding.destRecyclerView.adapter = recyclerViewItemsAdapter

        return destBinding.root
    }

    override fun onStart() {
        super.onStart()
        mMenuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_app_bar_search, menu)
                val searchFun = menu.findItem(R.id.search)
                val searchView: SearchView = searchFun.actionView as SearchView
                searchView.queryHint = "Search..."
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        performSearch(newText)
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.search -> return true

                }
                return false
            }

        }
        (requireActivity() as MenuHost).addMenuProvider(mMenuProvider)

    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as MenuHost).removeMenuProvider(mMenuProvider)
    }

    fun performSearch(query: String) {
        val filteredList = mutableListOf<Areas>()

        for (item in areasList) {
            if (item.toString().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
        } else {
            recyclerViewItemsAdapter.updateList(filteredList)

        }
    }
}