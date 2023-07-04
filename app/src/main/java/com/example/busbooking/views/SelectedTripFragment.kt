package com.example.busbooking.views

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.busbooking.dataclass.PassengerDetails
import com.example.busbooking.enums.SeatingType
import com.example.busbooking.R
import com.example.busbooking.viewmodels.SelectedTripFragmentViewModel
import com.example.busbooking.databinding.FragmentSelectedTripBinding


class SelectedTripFragment(private val selectedTripID: Int, private val seatingType: SeatingType) :
    Fragment() {

    private lateinit var binding: FragmentSelectedTripBinding
    private val selectedTripViewModel: SelectedTripFragmentViewModel by viewModels()
    private var toast: Toast? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSelectedTripBinding.inflate(inflater, container, false)

        val tripDetails = selectedTripViewModel.getTripDetails(selectedTripID)

        binding.busNameSelectedTrip.text = tripDetails.travelsName
        binding.busRatingSelectedTrip.text = tripDetails.busRating.toString()
        binding.boardingAreaSelectedTrip.text = tripDetails.boardingArea.toString() + " - "
        binding.droppingAreaSelectedTrip.text = tripDetails.droppingArea.toString()
        binding.dateSelectedTrip.text = tripDetails.dateSelectedTrip
        binding.boardingTimeSelectedTrip.text = tripDetails.boardingTime + " - "
        binding.droppingTimeSelectedTrip.text = tripDetails.droppingTIme

        val busLayout = binding.seatLayout

        binding.seatsSelectedLayout.visibility = View.GONE

        val linearLayoutParent = LinearLayout(requireContext())
        linearLayoutParent.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )

        linearLayoutParent.orientation = LinearLayout.VERTICAL
        (linearLayoutParent.layoutParams as LinearLayout.LayoutParams).setMargins(10, 10, 10, 10)

        val bookedSeats = selectedTripViewModel.getBookedSeats(selectedTripID)

        val perSeatPrice = selectedTripViewModel.getSeatPrice(selectedTripID)

        val seatsList = selectedTripViewModel.getSeatsList(selectedTripID)
        var seatsListCount = 0

        var noOfSeatsSelected = 0
        val selectedSeats: MutableList<Int> = mutableListOf()


        if (seatingType == SeatingType.Sleeper) {
            for (i in 0..5) {
                val linearLayoutChild = LinearLayout(requireContext())
                linearLayoutChild.layoutParams = LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                )
                linearLayoutChild.orientation = LinearLayout.HORIZONTAL
                (linearLayoutChild.layoutParams as LinearLayout.LayoutParams).setMargins(
                    0,
                    20,
                    0,
                    20
                )
                for (j in 1..3) {
                    val button = Button(requireContext())
                    button.layoutParams = LinearLayout.LayoutParams(200, 150)

                    Log.e(
                        "onclick",
                        "onclick ${seatsList[seatsListCount]} and total size is ${seatsList.size}"
                    )
                    button.tag = seatsList[seatsListCount]
                    ++seatsListCount
                    if (j == 1) {
                        (button.layoutParams as LinearLayout.LayoutParams).setMargins(
                            0,
                            0,
                            50,
                            0
                        )
                    }
                    var clicked = false
                    if (bookedSeats.contains(button.tag)) {
                        button.setBackgroundResource(R.drawable.sleeperbooked)
                        button.isClickable = false
                    } else {
                        button.setBackgroundResource(R.drawable.sleeperbeforeclick)
                        button.isClickable = true
                        button.setOnClickListener {
                            if (!clicked) {
                                if (noOfSeatsSelected > 5) {
                                    if (toast != null) {
                                        toast!!.cancel()
                                    }
                                    toast = Toast.makeText(
                                        requireContext(),
                                        "Maximum Number Of seats at one transaction can be only 6",
                                        Toast.LENGTH_SHORT
                                    )
                                    toast!!.show()
                                } else {
                                    button.setBackgroundResource(R.drawable.sleeperclicked)
                                    binding.seatsSelectedLayout.visibility = View.VISIBLE
                                    ++noOfSeatsSelected
                                    binding.noOfSeatsBooked.text = "$noOfSeatsSelected seats | "
                                    binding.totalPrice.text =
                                        (noOfSeatsSelected * perSeatPrice!!).toString()
                                    selectedSeats.add(button.tag as Int)
                                    var seatNumberString = ""
                                    for (seatNumber in selectedSeats) {
                                        seatNumberString += if (selectedSeats.size > 1) {
                                            if (seatNumber == selectedSeats.last()) {
                                                seatNumber.toString()
                                            } else {
                                                "$seatNumber ,"
                                            }
                                        } else {
                                            seatNumber.toString()
                                        }
                                    }
                                    binding.seatsNumbers.text = seatNumberString

                                    clicked = true
                                }
                            } else {
                                button.setBackgroundResource(R.drawable.sleeperbeforeclick)
                                --noOfSeatsSelected
                                selectedSeats.remove(button.tag as Int)
                                binding.totalPrice.text =
                                    (noOfSeatsSelected * perSeatPrice!!).toString()
                                if (noOfSeatsSelected == 0) {
                                    binding.seatsSelectedLayout.visibility = View.GONE
                                } else {
                                    binding.noOfSeatsBooked.text = "$noOfSeatsSelected seats |"
                                    var seatNumbersString = ""
                                    for (seatNumber in selectedSeats) {
                                        seatNumbersString += if (selectedSeats.size > 1) {
                                            if (seatNumber == selectedSeats.last()) {
                                                seatNumber.toString()
                                            } else {
                                                "$seatNumber ,"
                                            }
                                        } else {
                                            seatNumber.toString()
                                        }
                                    }
                                    binding.seatsNumbers.text = seatNumbersString
                                }
                                clicked = false
                            }
                        }
                    }
                    linearLayoutChild.addView(button)
                }
                linearLayoutParent.addView(linearLayoutChild)
            }
        } else if (seatingType == SeatingType.SemiSleeper) {
            for (i in 0..5) {
                val linearLayoutChild = LinearLayout(requireContext())
                linearLayoutChild.layoutParams = LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                )
                linearLayoutChild.orientation = LinearLayout.HORIZONTAL
                (linearLayoutChild.layoutParams as LinearLayout.LayoutParams).setMargins(
                    0,
                    20,
                    0,
                    20
                )
                for (j in 1..4) {

                    val button = Button(requireContext())
                    button.layoutParams = LinearLayout.LayoutParams(150, 150)

                    Log.e(
                        "onclick",
                        "onclick ${seatsList[seatsListCount]} and total size is ${seatsList.size}"
                    )
                    button.tag = seatsList[seatsListCount]
                    ++seatsListCount
                    if (j == 2) {
                        (button.layoutParams as LinearLayout.LayoutParams).setMargins(
                            0,
                            0,
                            50,
                            0
                        )
                    }
                    var clicked = false

                    if (bookedSeats.contains(button.tag)) {
                        button.setBackgroundResource(R.drawable.seaterbooked)
                        button.isClickable = false
                    } else {
                        button.setBackgroundResource(R.drawable.seaterbeforeclick)
                        button.isClickable = true

                        button.setOnClickListener {
                            if (!clicked) {
                                if (noOfSeatsSelected > 5) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Maximum Number Of seats at one transaction can be only 6",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    button.setBackgroundResource(R.drawable.seaterclicked)
                                    binding.seatsSelectedLayout.visibility = View.VISIBLE
                                    ++noOfSeatsSelected
                                    binding.noOfSeatsBooked.text = "$noOfSeatsSelected seats | "
                                    binding.totalPrice.text =
                                        (noOfSeatsSelected * perSeatPrice!!).toString()
                                    selectedSeats.add(button.tag as Int)
                                    var seatNumberString = ""
                                    for (seatNumber in selectedSeats) {
                                        seatNumberString += if (selectedSeats.size > 1) {
                                            if (seatNumber == selectedSeats.last()) {
                                                seatNumber.toString()
                                            } else {
                                                "$seatNumber ,"
                                            }
                                        } else {
                                            seatNumber.toString()
                                        }
                                    }
                                    binding.seatsNumbers.text = seatNumberString

                                    clicked = true
                                }
                            } else {
                                button.setBackgroundResource(R.drawable.seaterbeforeclick)
                                --noOfSeatsSelected
                                selectedSeats.remove(button.tag as Int)
                                binding.totalPrice.text =
                                    (noOfSeatsSelected * perSeatPrice!!).toString()
                                if (noOfSeatsSelected == 0) {
                                    binding.seatsSelectedLayout.visibility = View.GONE
                                } else {
                                    binding.noOfSeatsBooked.text = "$noOfSeatsSelected seats |"
                                    var seatNumbersString = ""
                                    for (seatNumber in selectedSeats) {
                                        seatNumbersString += if (selectedSeats.size > 1) {
                                            if (seatNumber == selectedSeats.last()) {
                                                seatNumber.toString()
                                            } else {
                                                "$seatNumber ,"
                                            }
                                        } else {
                                            seatNumber.toString()
                                        }
                                    }
                                    binding.seatsNumbers.text = seatNumbersString
                                }
                                clicked = false
                            }
                        }
                    }
                    linearLayoutChild.addView(button)
                }
                linearLayoutParent.addView(linearLayoutChild)
            }
        }

        busLayout.addView(linearLayoutParent)

        // CLICK SUBMIT BUTTON
        binding.submitButton.setOnClickListener {
            val passengerDetails: MutableList<PassengerDetails> =
                mutableListOf()
            for ((i, seatNumber) in selectedSeats.withIndex()) {
                passengerDetails.add(PassengerDetails("", "", "", seatNumber))
            }

            //todo : here i have passed the object itself to another fragment as parameter.

            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                PassengerDetailsFragment(passengerDetails, selectedTripID),
                "Passenger Details Frag"
            ).addToBackStack(null).commit()
//                requireActivity().supportFragmentManager.beginTransaction().replace(
//                    R.id.nav_host_fragment,
//                    PassengerDetailsFragment(selectedSeats),
//                    "Passenger Details Frag"
//                ).addToBackStack(null).commit()
        }
        return binding.root
    }
}
