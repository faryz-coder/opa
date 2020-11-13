package com.bit.onlinepandemicapps

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    val db = Firebase.firestore

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_first, container, false)

        val dateSelect = root.findViewById<TextView>(R.id.selectDate)
        val progressBar = root.findViewById<ProgressBar>(R.id.progressBar)

        val covidCase = root.findViewById<TextView>(R.id.covidCase)
        val covidCure = root.findViewById<TextView>(R.id.covidCure)
        val covidDeath = root.findViewById<TextView>(R.id.covidDeath)

        val malariaCase = root.findViewById<TextView>(R.id.malariaCase)
        val malariaCure = root.findViewById<TextView>(R.id.malariaCure)
        val malariaDeath = root.findViewById<TextView>(R.id.malariaDeath)

        val denggiCase = root.findViewById<TextView>(R.id.denggiCase)
        val denggiCure = root.findViewById<TextView>(R.id.denggiCure)
        val denggiDeath = root.findViewById<TextView>(R.id.denggiDeath)

        val defaultDate = (activity as MainActivity).defaultDate

        fun showData(date : String) {
            db.collection("Case").document("$date")
                .get()
                .addOnSuccessListener {
                    val cCase = it.getField<String>("covidCase")
                    val cCure = it.getField<String>("covidCure")
                    val cDeath = it.getField<String>("covidDeath")

                    val mCase = it.getField<String>("malariaCase")
                    val mCure = it.getField<String>("malariaCure")
                    val mDeath = it.getField<String>("malariaDeath")

                    val dCase = it.getField<String>("denggiCase")
                    val dCure = it.getField<String>("denggiCure")
                    val dDeath = it.getField<String>("denggiDeath")

                    covidCase.text = cCase
                    covidCure.text = cCure
                    covidDeath.text = cDeath

                    malariaCase.text = mCase
                    malariaCure.text = mCure
                    malariaDeath.text = mDeath

                    denggiCase.text =  dCase
                    denggiCure.text =  dCure
                    denggiDeath.text = dDeath

                    progressBar.isVisible = false
                }
        }

        if (!defaultDate.isNullOrEmpty()) {
            dateSelect.text = defaultDate
            showData(defaultDate)
        }

        dateSelect.setOnClickListener {
            val cal = Calendar.getInstance()
            val d = cal.get(Calendar.DAY_OF_MONTH)
            val m = cal.get(Calendar.MONTH)
            val y = cal.get(Calendar.YEAR)

            val viewDate = DatePickerDialog(root.context, DatePickerDialog.OnDateSetListener { _, y, m, d ->
                progressBar.isVisible = true
                val selectedDate = "$d-${m+1}-$y"
                dateSelect.text = selectedDate
                showData(selectedDate)
                (activity as MainActivity).defaultDate = selectedDate
            }, y,m,d)
            viewDate.show()

        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
//
//        view.findViewById<Button>(R.id.onMyLocation).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_thirdFragment)
//        }
    }
}