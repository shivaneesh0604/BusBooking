package com.example.busbooking.recyclerview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.busbooking.model.PassengerDetails
import com.example.busbooking.R
import com.google.android.material.textfield.TextInputEditText

class PassengerDetailsFragmentRecyclerView(
    private val context: Context,
    private val passengerDetails: List<PassengerDetails>
) : RecyclerView.Adapter<PassengerDetailsFragmentRecyclerView.PassengerDetailsViewHolder>() {

    lateinit var passengerDetailsViewHolder: PassengerDetailsViewHolder
    inner class PassengerDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(currentItem: PassengerDetails) {
            val editableTextPassengerName =
                Editable.Factory.getInstance().newEditable(currentItem.passengerName)

            val editableTextPassengerAge =
                Editable.Factory.getInstance().newEditable(currentItem.passengerAge)

            passengerName.text = editableTextPassengerName
            passengerAge.text = editableTextPassengerAge
            "Seat Number ${currentItem.seatNumber}".also { seatNumber.text = it }

        }

        private val seatNumber: TextView = itemView.findViewById(R.id.seatNumber)
        private val passengerName: TextInputEditText = itemView.findViewById(R.id.passengerName)
        private val radioButton: RadioGroup = itemView.findViewById(R.id.radioGroupGender)
        private val passengerAge: TextInputEditText = itemView.findViewById(R.id.passengerAge)
        val genderMale:RadioButton = itemView.findViewById(R.id.maleGender)
        val genderFemale:RadioButton = itemView.findViewById(R.id.femaleGender)

        init {
            passengerName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        this@PassengerDetailsFragmentRecyclerView.passengerDetails[position].passengerName =
                            s.toString()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            radioButton.setOnCheckedChangeListener { group, checkedId ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val radioButton = group.findViewById<RadioButton>(checkedId)
                    val radioButtonValue = radioButton.text.toString()
                    this@PassengerDetailsFragmentRecyclerView.passengerDetails[position].gender =
                        radioButtonValue
                }
            }

            passengerAge.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        this@PassengerDetailsFragmentRecyclerView.passengerDetails[position].passengerAge =
                            s.toString()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

        }

//        fun validateFields() {
//            // TODO: need to check this
//            val passengerName = passengerName.text.toString()
//            val passengerAge = passengerAge.text.toString()
//            val radioButtonSelected = radioButton.checkedRadioButtonId != -1
//
//            if (passengerName.isEmpty()) {
//                this.passengerName.error = "Please enter Name"
//            }
//
//            if (passengerAge.isEmpty()){
//                this.passengerAge.error = "Please enter Age"
//            }
//
//            if (!radioButtonSelected) {
//                // Show error for unselected radio button
//                // You can customize this logic based on your requirements
//                val errorText = "Please select an option"
//                val radioButton1 = itemView.findViewById<RadioButton>(R.id.maleGender)
//                val radioButton2 = itemView.findViewById<RadioButton>(R.id.femaleGender)
//                radioButton1.error = errorText
//                radioButton2.error = errorText
//            }
//
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerDetailsViewHolder {
        passengerDetailsViewHolder =  PassengerDetailsViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.passenger_details_recyclerview, parent, false)
        )

        return passengerDetailsViewHolder
    }

    override fun getItemCount(): Int {
        return passengerDetails.size
    }

    override fun onBindViewHolder(holder: PassengerDetailsViewHolder, position: Int) {
        val currentItem = passengerDetails[position]

        if (currentItem.gender == "Male"){
            holder.genderMale.isChecked = true
        }else if (currentItem.gender == "Female"){
            holder.genderFemale.isChecked = true
        }
        holder.bindData(currentItem)
    }

    fun getPassengerDetails():List<PassengerDetails>{
        return passengerDetails
    }
}