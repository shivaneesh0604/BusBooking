package com.example.busbooking.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.example.busbooking.Listener.DatePickerBottomSheetListener
import com.example.busbooking.databinding.FragmentDatePickerBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerBottomSheet(private val datePickerBottomSheetListener: DatePickerBottomSheetListener) :
    BottomSheetDialogFragment() {

    private lateinit var datePickerBottomSheetBinding: FragmentDatePickerBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        datePickerBottomSheetBinding =
            FragmentDatePickerBottomSheetBinding.inflate(inflater, container, false)

        val datePicker = datePickerBottomSheetBinding.datePicker

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 5)
        val maxDate = calendar.timeInMillis

        val today = Calendar.getInstance()
        val minDate = today.timeInMillis

        datePicker.minDate = minDate
        datePicker.maxDate = maxDate


        val submitButton = datePickerBottomSheetBinding.okButtonDatePicker

        submitButton.setOnClickListener {
            val selectedDate = getFormattedDateFromDatePicker(datePicker)
            datePickerBottomSheetListener.selectedDate(selectedDate)
            dismiss()
        }

        return datePickerBottomSheetBinding.root
    }

    private fun getFormattedDateFromDatePicker(datePicker: DatePicker): String {

        val year = datePicker.year
        val month = datePicker.month
        val day = datePicker.dayOfMonth

        // Create a Calendar instance and set the selected date
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

//        // Set the minimum date to today
//        datePicker.minDate = calendar.timeInMillis

        // Create a SimpleDateFormat with the desired date format
        val dateFormat = SimpleDateFormat("E, dd MMM", Locale.getDefault())

        // Format the date and return it
        return dateFormat.format(calendar.time)
    }
}