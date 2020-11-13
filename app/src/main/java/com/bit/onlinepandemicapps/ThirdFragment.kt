package com.bit.onlinepandemicapps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment() {
    val db = Firebase.firestore
    lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.context)

        fun getCase(address: String, local: String) {
            view.findViewById<TextView>(R.id.currentLocation).text = address

            db.collection("Location").document("$local")
                .get()
                .addOnSuccessListener {
                    val case = it.getField<String>("case")
                    view.findViewById<TextView>(R.id.locationCase).text = case
                    if (case.isNullOrEmpty()) {
                        view.findViewById<TextView>(R.id.locationCase).text = "0"
                    }
                }
        }

        view.findViewById<Button>(R.id.buttonGetCurrentLocation).setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    view.context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    view.context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
            fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    d("bomoh", "location: $it")
                    if (it != null) {
                        val geocoder = Geocoder(view.context, Locale.getDefault())
                        val location = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                        d("bomoh", "location: $location")
                        for (locations in location) {
                            val addLine = locations.getAddressLine(0)
                            val local = locations.locality
                            d("bomoh", "location: $local")
                            getCase(addLine, local)
                        }


                    }
                }
        }
    }


}