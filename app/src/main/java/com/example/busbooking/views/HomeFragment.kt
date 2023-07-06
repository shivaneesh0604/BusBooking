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
import androidx.lifecycle.ViewModelProvider
import com.example.busbooking.enums.TripLocation
import com.example.busbooking.R
import com.example.busbooking.viewmodels.HomeViewModel
import com.example.busbooking.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val dateFormatToday = SimpleDateFormat("E, dd MMM", Locale.getDefault())

        homeBinding.dateID.text = dateFormatToday.format(calendar.time)
        if (homeViewModel.selectedDay == null) {
            homeViewModel.selectedDay = dateFormatToday.format(calendar.time)
        }

        homeBinding.sourceID.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, SourceFragment(), "SourceFragment")
                .addToBackStack("SourceFragment").commit()
        }

        homeBinding.destID.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, DestinationFragment(), "DestFragment")
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
                        .replace(
                            R.id.nav_host_fragment,
                            selectedBusRouteFragment,
                            "SelectedBusRoute"
                        )
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

    override fun onResume() {
        super.onResume()
        Log.e("dateCheck", "dateCheck in onresume")
        homeBinding.dateID.text = homeViewModel.selectedDay

        homeBinding.destID.text = homeViewModel.selectedDestination

        homeBinding.sourceID.text = homeViewModel.selectedSource

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.bookingsFrameLayout, BookingsFragment()).addToBackStack(null).commit()
    }

    private fun checkNullValidationForRoutes(): TripLocation? {
        if (homeBinding.sourceID.text.isEmpty() || homeBinding.sourceID.text == homeBinding.destID.text) {
            return TripLocation.Source
        } else if (homeBinding.destID.text.isEmpty()) {
            return TripLocation.Destination
        }
        return null
    }

    private fun showDatePickerDialog() {
        // Get the current year, month, and day from the Calendar instance
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("E, dd MMM", Locale.getDefault())

                homeBinding.dateID.text = dateFormat.format(selectedCalendar.time)
                homeViewModel.selectedDay = dateFormat.format(selectedCalendar.time)
                Log.e("dateCheck", "dateCheck in setting function")
            },
            currentYear,
            currentMonth,
            currentDay
        )

        val maxDateCalendar = Calendar.getInstance()
        maxDateCalendar.add(Calendar.DAY_OF_MONTH, 5)
        datePickerDialog.datePicker.maxDate = maxDateCalendar.timeInMillis

        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.set(currentYear, currentMonth, currentDay)
        datePickerDialog.datePicker.minDate = minDateCalendar.timeInMillis

        datePickerDialog.show()
    }


    fun setSelectedTripLocationData(selectedTripLocation: String, tripLocation: TripLocation) {
        if (tripLocation == TripLocation.Source) {
            homeViewModel.selectedSource = selectedTripLocation
        } else {
            homeViewModel.selectedDestination = selectedTripLocation
        }
    }
}