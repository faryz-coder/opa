package com.bit.onlinepandemicapps

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    val db = Firebase.firestore
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val map = view.findViewById<PhotoView>(R.id.mapImage)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar2)
        progressBar.isVisible = true

        val recyclerView = view.findViewById<RecyclerView>(R.id.tableRecycleView)

        val state = mutableListOf<State>()

        fun rv() {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@SecondFragment.context)
                adapter = StateAdapter(state)
            }
        }

        db.collection("Map").document("image")
            .get()
            .addOnSuccessListener {
                val url = it.getField<String>("url").toString()
                Picasso.get().load(url).into(map)
                progressBar.isVisible = false
            }

        db.collection("Map").document("State")
                .get()
                .addOnSuccessListener {
                    for (results in it.data!!.iterator()) {
                        val stateName = results.key.toString()
                        val stateZone = results.value.toString()
                        d("bomoh", "result: $stateName, $stateZone")
                        state.add(State(stateName, stateZone))
                    }
                    rv()
                }


//        view.findViewById<Button>(R.id.button_second).setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }
}