package com.example.busbooking.views

import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.busbooking.enums.TripLocation
import com.example.busbooking.listener.DatePickerBottomSheetListener
import com.example.busbooking.R
import com.example.busbooking.recyclerviews.TripLocationFragmentRecyclerView
import com.example.busbooking.viewmodels.HomeViewModel
import com.example.busbooking.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(), DatePickerBottomSheetListener,
    TripLocationFragmentRecyclerView.TripClickListener {

    private lateinit var homeBinding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val dateFormatToday = SimpleDateFormat("E, dd MMM", Locale.getDefault())


        homeBinding.dateID.text = dateFormatToday.format(calendar.time)
        homeViewModel.selectedDay = dateFormatToday.format(calendar.time)

        if (homeViewModel.selectedDay != null) {
            homeBinding.dateID.text = homeViewModel.selectedDay
        }

        if (homeViewModel.selectedDestination != null) {
            homeBinding.destID.text = homeViewModel.selectedDestination
        }

        if (homeViewModel.selectedSource != null) {
            homeBinding.sourceID.text = homeViewModel.selectedSource
        }

        homeBinding.sourceID.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, SourceFragment(this), "SourceFragment")
                .addToBackStack(null).commit()
        }

        homeBinding.destID.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, DestinationFragment(this), "DestFragment")
                .addToBackStack(null).commit()
        }

        homeBinding.dateID.setOnClickListener {
            showDatePickerDialog()
        }

        homeBinding.todayBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("E, dd MMM", Locale.getDefault())

            homeBinding.dateID.text = dateFormat.format(calendar.time)
            homeViewModel.selectedDay = dateFormat.format(calendar.time)
        }

        homeBinding.tomorrowBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("E, dd MMM", Locale.getDefault())
            calendar.add(Calendar.DAY_OF_MONTH, 1)

            homeBinding.dateID.text = dateFormat.format(calendar.time)
            homeViewModel.selectedDay = dateFormat.format(calendar.time)
        }

        homeBinding.reverseSourceAndDest.setOnClickListener {
            val rotationAnimator =
                ObjectAnimator.ofFloat(homeBinding.reverseSourceAndDest, "rotation", 0f, 180f)
            rotationAnimator.duration = 500 // Animation duration in milliseconds
            rotationAnimator.start()
            val sourceLocation = homeViewModel.selectedSource
            val destinationLocation = homeViewModel.selectedDestination

            homeViewModel.selectedSource = destinationLocation
            homeViewModel.selectedDestination = sourceLocation

            homeBinding.sourceID.text = homeViewModel.selectedSource
            homeBinding.destID.text = homeViewModel.selectedDestination
        }

        homeBinding.searchBuses.setOnClickListener {

            when (checkNullValidationForRoutes()) {
                null -> {

                    val selectedBusRouteFragment = SelectedBusRouteFragment()
                    val selectedBusRoutes = Bundle()
                    selectedBusRoutes.putString(
                        "boardingLocation", homeBinding.sourceID.text as String?
                    )
                    selectedBusRoutes.putString(
                        "droppingLocation", homeBinding.destID.text as String?
                    )
                    selectedBusRoutes.putString(
                        "selectedDate", homeBinding.dateID.text as String?
                    )
                    selectedBusRouteFragment.arguments = selectedBusRoutes
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment,selectedBusRouteFragment, "SelectedBusRoute")
                        .addToBackStack(null).commit()
                }
                TripLocation.Source -> {
                    val warningIcon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.baseline_warning_24)
                    homeBinding.sourceID.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        warningIcon,
                        null,
                        null,
                        null
                    )
                }
                TripLocation.Destination -> {
                    val warningIcon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.baseline_warning_24)
                    homeBinding.destID.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        warningIcon,
                        null,
                        null,
                        null
                    )
                }
            }
        }

        return homeBinding.root
    }

    private fun checkNullValidationForRoutes(): TripLocation? {
        if (homeBinding.sourceID.text.isEmpty() || homeBinding.sourceID.text == homeBinding.destID.text) {
            return TripLocation.Source
        } else if (homeBinding.destID.text.isEmpty()) {
            return TripLocation.Destination
        }
        return null
    }

    override fun selectedDate(date: String) {
        homeBinding.dateID.text = date
        homeViewModel.selectedDay = date
    }

    override fun selectedTripLocation(selectedTripLocation: String, tripLocation: TripLocation) {
        requireActivity().supportFragmentManager.popBackStack()
        if (tripLocation == TripLocation.Source) {
            homeBinding.sourceID.text = selectedTripLocation
            homeViewModel.selectedSource = selectedTripLocation
        } else {
            homeBinding.destID.text = selectedTripLocation
            homeViewModel.selectedDestination = selectedTripLocation
        }
    }

    private fun showDatePickerDialog() {
        // Get the current year, month, and day from the Calendar instance
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        // Set the range for the date picker to 5 days from today
        val maxDateCalendar = Calendar.getInstance()
        maxDateCalendar.add(Calendar.DAY_OF_MONTH, 5)


        // Create a date picker dialog and set the initial date to the current date
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                // Handle the selected date
                // Here, you can perform any actions with the selected date
                // For example, update a TextView with the selected date
                val calendar = Calendar.getInstance()
                calendar.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("E, dd MMM", Locale.getDefault())

                homeBinding.dateID.text = dateFormat.format(calendar.time)
                this.calendar.set(selectedYear, selectedMonth, selectedDay)
                homeViewModel.selectedDay = dateFormat.format(calendar.time)
                Log.e("datecheck",""+homeViewModel.selectedDay)
            },
            currentYear,
            currentMonth,
            currentDay
        )

        // Set the maximum date allowed in the date picker
        datePickerDialog.datePicker.maxDate = maxDateCalendar.timeInMillis
        datePickerDialog.datePicker.minDate = calendar.timeInMillis

        // Show the date picker dialog
        datePickerDialog.show()
    }
}