package com.example.busbooking.views

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busbooking.enums.Areas
import com.example.busbooking.enums.TripLocation
import com.example.busbooking.R
import com.example.busbooking.recyclerview.TripLocationFragmentRecyclerView
import com.example.busbooking.databinding.FragmentSourceBinding
import java.util.*

class SourceFragment : TripLocationFragmentRecyclerView.TripLocationClickListener,
    Fragment() {

    private lateinit var sourceBinding: FragmentSourceBinding
    private lateinit var mMenuProvider: MenuProvider
    private val areasList: MutableList<Areas> = mutableListOf()
    private lateinit var recyclerViewItemsAdapter: TripLocationFragmentRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        for (i in Areas.values()) {
            areasList.add(i)
        }

        sourceBinding = FragmentSourceBinding.inflate(inflater, container, false)

        sourceBinding.sourceRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewItemsAdapter =
            TripLocationFragmentRecyclerView(
                requireContext(), areasList as List<Areas>, this,
                TripLocation.Source
            )

        sourceBinding.sourceRecyclerView.adapter = recyclerViewItemsAdapter

        return sourceBinding.root
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
            if (item.toString().lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
        } else {

            recyclerViewItemsAdapter.updateList(filteredList)


        }
    }

    override fun selectedTripLocation(selectedtripLocation: String, tripLocation: TripLocation) {

        val homeFragment =
            requireActivity().supportFragmentManager.findFragmentByTag("HomeFragment")
        Log.e("homeVm", "check home Frag $homeFragment")
        if (homeFragment is HomeFragment) {
            homeFragment.setSelectedTripLocationData(selectedtripLocation, tripLocation)
        }
        requireActivity().supportFragmentManager.popBackStack()
    }
}

