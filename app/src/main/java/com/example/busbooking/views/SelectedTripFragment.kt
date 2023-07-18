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
import com.example.busbooking.R
import com.example.busbooking.databinding.FragmentSelectedTripBinding
import com.example.busbooking.enums.SeatingType
import com.example.busbooking.model.PassengerDetails
import com.example.busbooking.viewmodels.SelectedTripFragmentViewModel


class SelectedTripFragment :
    Fragment() {

    private lateinit var binding: FragmentSelectedTripBinding
    private val selectedTripViewModel: SelectedTripFragmentViewModel by viewModels()
    private var toast: Toast? = null
    private var selectedTripID: Int = 0
    private lateinit var seatingType: SeatingType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedTripID = it.getInt("tripID")
            val seatingTypeGot = it.getString("seatingType")
            if (seatingTypeGot == "SemiSleeper") {
                seatingType = SeatingType.SemiSleeper
            } else if (seatingTypeGot == "Sleeper") {
                seatingType = SeatingType.Sleeper
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSelectedTripBinding.inflate(inflater, container, false)

    // TODO: check with right side movement
//        binding.nextButton?.setOnClickListener{
//            it.scrollTo(
//                it.scrollX + 10,
//                it.scrollY
//            )
//        }


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
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        )

        linearLayoutParent.orientation = LinearLayout.VERTICAL
        (linearLayoutParent.layoutParams as LinearLayout.LayoutParams).setMargins(10, 10, 10, 10)

        val bookedSeats = selectedTripViewModel.getBookedSeats(selectedTripID)

        val perSeatPrice = selectedTripViewModel.getSeatPrice(selectedTripID)

        val seatsList = selectedTripViewModel.getSeatsList(selectedTripID)

//        val selectedSeatsVM = selectedTripViewModel.getSelectedSeats()
        Log.e("selectedSeatsVM", "selectedSeatsVM ${selectedTripViewModel.getSelectedSeats()}")
        var seatsListCount = 0

//        val noOfSeatsSelected = selectedSeatsVM.size
//        val selectedSeats: MutableList<Int> = selectedTripViewModel.getSelectedSeats()

        if (seatingType == SeatingType.Sleeper) {

            for (i in 0 until seatsList.size / 3) {
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
                    if (seatsListCount < seatsList.size) {
                        val button = Button(requireContext())
                        button.layoutParams = LinearLayout.LayoutParams(200, 150)
                        button.tag = seatsList[seatsListCount]
                        ++seatsListCount
                        var clicked = false
                        if (bookedSeats.contains(button.tag)) {
                            button.setBackgroundResource(R.drawable.sleeperbooked)
                            button.isClickable = false
                        } else {
                            if (selectedTripViewModel.getSelectedSeats().contains(button.tag)) {
                                button.setBackgroundResource(R.drawable.sleeperclicked)
                                binding.seatsSelectedLayout.visibility = View.VISIBLE
                                binding.noOfSeatsBooked.text = "${selectedTripViewModel.getSelectedSeats().size} seats | "
                                binding.totalPrice.text =
                                    "₹"+(selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                                var seatNumberString = ""
                                for (seatNumber in selectedTripViewModel.getSelectedSeats()) {
                                    seatNumberString += if (selectedTripViewModel.getSelectedSeats().size > 1) {
                                        if (seatNumber == selectedTripViewModel.getSelectedSeats().last()) {
                                            seatNumber
                                        } else {
                                            "$seatNumber ,"
                                        }
                                    } else {
                                        seatNumber
                                    }
                                }
                                binding.seatsNumbers.text = seatNumberString
                                clicked = true
                            } else {
                                button.setBackgroundResource(R.drawable.sleeperbeforeclick)
                            }
                            button.isClickable = true
                            button.setOnClickListener {
                                if (!clicked) {
                                    if (selectedTripViewModel.getSelectedSeats().size > 5) {
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
                                        selectedTripViewModel.addSelectedSeats(button.tag as Int)
                                        binding.seatsSelectedLayout.visibility = View.VISIBLE
                                        binding.noOfSeatsBooked.text = "${selectedTripViewModel.getSelectedSeats().size} seats | "
                                        binding.totalPrice.text =
                                            "₹"+(selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                                        var seatNumberString = ""
                                        val selectedSeats = selectedTripViewModel.getSelectedSeats()
                                        for (seatNumber in selectedSeats) {
                                            seatNumberString += if (selectedSeats.size > 1) {
                                                if (seatNumber == selectedSeats.last()) {
                                                    seatNumber
                                                } else {
                                                    "$seatNumber ,"
                                                }
                                            } else {
                                                seatNumber
                                            }
                                        }
                                        binding.seatsNumbers.text = seatNumberString

                                        clicked = true
                                    }
                                } else {
                                    Log.e(
                                        "selectedSeatsVM",
                                        "check removal before ${selectedTripViewModel.getSelectedSeats()}"
                                    )
                                    button.setBackgroundResource(R.drawable.sleeperbeforeclick)
                                    selectedTripViewModel.removeSelectedSeats(button.tag as Int)
                                    Log.e(
                                        "selectedSeatsVM",
                                        "check removal after ${selectedTripViewModel.getSelectedSeats()}"
                                    )
                                    binding.totalPrice.text =
                                        "₹" + (selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                                    if (selectedTripViewModel.getSelectedSeats().isEmpty()) {
                                        binding.seatsSelectedLayout.visibility = View.GONE
                                    } else {
                                        binding.noOfSeatsBooked.text =
                                            "${selectedTripViewModel.getSelectedSeats().size} seats |"
                                        var seatNumbersString = ""
                                        val selectedSeats = selectedTripViewModel.getSelectedSeats()
                                        for (seatNumber in selectedSeats) {
                                            seatNumbersString += if (selectedSeats.size > 1) {
                                                if (seatNumber == selectedSeats.last()) {
                                                    seatNumber
                                                } else {
                                                    "$seatNumber ,"
                                                }
                                            } else {
                                                seatNumber
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
                }
                linearLayoutParent.addView(linearLayoutChild)
            }
            if (seatsList.size % 3 != 0) {
                val remainingSeatsCount = seatsList.size % 3
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
                for (j in 0 until remainingSeatsCount) {
                    val button = Button(requireContext())
                    button.layoutParams = LinearLayout.LayoutParams(200, 150)
                    button.tag = seatsList[seatsListCount]
                    ++seatsListCount
                    var clicked = false
                    if (bookedSeats.contains(button.tag)) {
                        button.setBackgroundResource(R.drawable.sleeperbooked)
                        button.isClickable = false
                    } else {
                        if (selectedTripViewModel.getSelectedSeats().contains(button.tag)) {
                            button.setBackgroundResource(R.drawable.sleeperclicked)
                            binding.seatsSelectedLayout.visibility = View.VISIBLE
                            binding.noOfSeatsBooked.text =
                                "${selectedTripViewModel.getSelectedSeats().size} seats | "
                            binding.totalPrice.text =
                                "₹" + (selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                            clicked = true
                        } else {
                            button.setBackgroundResource(R.drawable.sleeperbeforeclick)
                        }
                        button.isClickable = true
                        button.setOnClickListener {
                            if (!clicked) {
                                if (selectedTripViewModel.getSelectedSeats().size > 5) {
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
                                    selectedTripViewModel.addSelectedSeats(button.tag as Int)
                                    binding.seatsSelectedLayout.visibility = View.VISIBLE
                                    binding.noOfSeatsBooked.text =
                                        "${selectedTripViewModel.getSelectedSeats().size} seats | "
                                    binding.totalPrice.text =
                                        "₹" + (selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                                    var seatNumberString = ""
                                    val selectedSeats = selectedTripViewModel.getSelectedSeats()
                                    for (seatNumber in selectedSeats) {
                                        seatNumberString += if (selectedSeats.size > 1) {
                                            if (seatNumber == selectedSeats.last()) {
                                                seatNumber
                                            } else {
                                                "$seatNumber ,"
                                            }
                                        } else {
                                            seatNumber
                                        }
                                    }
                                    binding.seatsNumbers.text = seatNumberString

                                    clicked = true
                                }
                            } else {
                                button.setBackgroundResource(R.drawable.sleeperbeforeclick)
                                selectedTripViewModel.removeSelectedSeats(button.tag as Int)
                                binding.totalPrice.text =
                                    "₹" + (selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                                if (selectedTripViewModel.getSelectedSeats().isEmpty()) {
                                    binding.seatsSelectedLayout.visibility = View.GONE
                                } else {
                                    binding.noOfSeatsBooked.text =
                                        "${selectedTripViewModel.getSelectedSeats().size} seats |"
                                    var seatNumbersString = ""
                                    val selectedSeats = selectedTripViewModel.getSelectedSeats()
                                    for (seatNumber in selectedSeats) {
                                        seatNumbersString += if (selectedSeats.size > 1) {
                                            if (seatNumber == selectedSeats.last()) {
                                                seatNumber
                                            } else {
                                                "$seatNumber ,"
                                            }
                                        } else {
                                            seatNumber
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
//            Log.e("size check", "size ${seatsList.size / 4}")

            for (i in 0 until seatsList.size / 4) {
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
                    if (seatsListCount < seatsList.size) {
                        val button = Button(requireContext())
                        button.layoutParams = LinearLayout.LayoutParams(150, 150)
                        button.tag = seatsList[seatsListCount]
                        ++seatsListCount
                        var clicked = false

                        if (bookedSeats.contains(button.tag)) {
                            button.setBackgroundResource(R.drawable.seaterbooked)
                            button.isClickable = false
                        } else {
                            if (selectedTripViewModel.getSelectedSeats().contains(button.tag)) {
                                button.setBackgroundResource(R.drawable.seaterclicked)
                                binding.seatsSelectedLayout.visibility = View.VISIBLE
                                binding.noOfSeatsBooked.text =
                                    "${selectedTripViewModel.getSelectedSeats().size} seats | "
                                binding.totalPrice.text =
                                    "₹" + (selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                                var seatNumberString = ""
                                val selectedSeats = selectedTripViewModel.getSelectedSeats()
                                for (seatNumber in selectedSeats) {
                                    seatNumberString += if (selectedSeats.size > 1) {
                                        if (seatNumber == selectedSeats.last()) {
                                            seatNumber
                                        } else {
                                            "$seatNumber ,"
                                        }
                                    } else {
                                        seatNumber
                                    }
                                }
                                binding.seatsNumbers.text = seatNumberString
                                clicked = true
                            } else {
                                button.setBackgroundResource(R.drawable.seaterbeforeclick)
                            }
                            button.isClickable = true

                            button.setOnClickListener {
                                if (!clicked) {
                                    if (selectedTripViewModel.getSelectedSeats().size > 5) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Maximum Number Of seats at one transaction can be only 6",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        button.setBackgroundResource(R.drawable.seaterclicked)
                                        selectedTripViewModel.addSelectedSeats(button.tag as Int)
                                        binding.seatsSelectedLayout.visibility = View.VISIBLE
                                        binding.noOfSeatsBooked.text =
                                            "${selectedTripViewModel.getSelectedSeats().size} seats | "
                                        binding.totalPrice.text =
                                            "₹" + (selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                                        var seatNumberString = ""
                                        val selectedSeats = selectedTripViewModel.getSelectedSeats()
                                        for (seatNumber in selectedSeats) {
                                            seatNumberString += if (selectedSeats.size > 1) {
                                                if (seatNumber == selectedSeats.last()) {
                                                    seatNumber
                                                } else {
                                                    "$seatNumber ,"
                                                }
                                            } else {
                                                seatNumber
                                            }
                                        }
                                        binding.seatsNumbers.text = seatNumberString

                                        clicked = true
                                    }
                                } else {
                                    button.setBackgroundResource(R.drawable.seaterbeforeclick)
                                    selectedTripViewModel.removeSelectedSeats(button.tag as Int)
                                    binding.totalPrice.text =
                                        "₹" + (selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                                    if (selectedTripViewModel.getSelectedSeats().isEmpty()) {
                                        binding.seatsSelectedLayout.visibility = View.GONE
                                    } else {
                                        binding.noOfSeatsBooked.text =
                                            "${selectedTripViewModel.getSelectedSeats().size} seats |"
                                        var seatNumbersString = ""
                                        val selectedSeats = selectedTripViewModel.getSelectedSeats()
                                        for (seatNumber in selectedSeats) {
                                            seatNumbersString += if (selectedSeats.size > 1) {
                                                if (seatNumber == selectedSeats.last()) {
                                                    seatNumber
                                                } else {
                                                    "$seatNumber ,"
                                                }
                                            } else {
                                                seatNumber
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
                }
                linearLayoutParent.addView(linearLayoutChild)
            }
//            Log.e("size check", "size ${seatsList.size % 4}")
            if (seatsList.size % 4 != 0) {

                val remainingSeatsCount = seatsList.size % 4

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
                for (j in 0 until remainingSeatsCount) {
                    val button = Button(requireContext())
                    button.layoutParams = LinearLayout.LayoutParams(150, 150)
                    button.tag = seatsList[seatsListCount]
                    ++seatsListCount
                    var clicked = false
                    if (bookedSeats.contains(button.tag)) {
                        button.setBackgroundResource(R.drawable.sleeperbooked)
                        button.isClickable = false
                    } else {
                        if (selectedTripViewModel.getSelectedSeats().contains(button.tag)) {
                            button.setBackgroundResource(R.drawable.seaterclicked)
                            binding.seatsSelectedLayout.visibility = View.VISIBLE
                            binding.noOfSeatsBooked.text =
                                "${selectedTripViewModel.getSelectedSeats().size} seats | "
                            binding.totalPrice.text =
                                "₹" + (selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                            clicked = true
                        } else {
                            button.setBackgroundResource(R.drawable.seaterbeforeclick)
                        }
                        button.isClickable = true
                        button.setOnClickListener {
                            if (!clicked) {
                                if (selectedTripViewModel.getSelectedSeats().size > 5) {
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
                                    selectedTripViewModel.addSelectedSeats(button.tag as Int)
                                    button.setBackgroundResource(R.drawable.seaterclicked)
                                    binding.seatsSelectedLayout.visibility = View.VISIBLE
                                    binding.noOfSeatsBooked.text =
                                        "${selectedTripViewModel.getSelectedSeats().size} seats | "
                                    binding.totalPrice.text =
                                        "₹" + (selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                                    var seatNumberString = ""
                                    val selectedSeats = selectedTripViewModel.getSelectedSeats()
                                    for (seatNumber in selectedSeats) {
                                        seatNumberString += if (selectedSeats.size > 1) {
                                            if (seatNumber == selectedSeats.last()) {
                                                seatNumber
                                            } else {
                                                "$seatNumber ,"
                                            }
                                        } else {
                                            seatNumber
                                        }
                                    }
                                    binding.seatsNumbers.text = seatNumberString

                                    clicked = true
                                }
                            } else {
                                button.setBackgroundResource(R.drawable.seaterbeforeclick)
                                selectedTripViewModel.removeSelectedSeats(button.tag as Int)
                                binding.totalPrice.text =
                                    "₹" + (selectedTripViewModel.getSelectedSeats().size * perSeatPrice!!).toString()
                                if (selectedTripViewModel.getSelectedSeats().isEmpty()) {
                                    binding.seatsSelectedLayout.visibility = View.GONE
                                } else {
                                    binding.noOfSeatsBooked.text =
                                        "${selectedTripViewModel.getSelectedSeats().size} seats |"
                                    var seatNumbersString = ""
                                    val selectedSeats = selectedTripViewModel.getSelectedSeats()
                                    for (seatNumber in selectedSeats) {
                                        seatNumbersString += if (selectedSeats.size > 1) {
                                            if (seatNumber == selectedSeats.last()) {
                                                seatNumber
                                            } else {
                                                "$seatNumber ,"
                                            }
                                        } else {
                                            seatNumber
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
            val passengerDetails: ArrayList<PassengerDetails> =
                arrayListOf()
            for (seatNumber in selectedTripViewModel.getSelectedSeats()) {
                passengerDetails.add(PassengerDetails("", "", "", seatNumber))
            }

            val passengerDetailsFragment = PassengerDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("passengerDetails", passengerDetails)
            bundle.putInt("tripId", selectedTripID)
            passengerDetailsFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                passengerDetailsFragment,
                "Passenger Details Frag"
            ).addToBackStack(null).commit()
        }
        return binding.root
    }
}
