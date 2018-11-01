package farhan.example.com.practicemapgeocode

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var mapReady: Boolean = false
    private var marker: MarkerOptions? = null
    private var cameraPosition: CameraPosition? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = fragmentManager.findFragmentById(R.id.mapFrag) as MapFragment
        mapFragment.getMapAsync(this)


        edtAddress.setOnTouchListener(View.OnTouchListener { _, event ->
            //val DRAWABLE_LEFT = 0
            //val DRAWABLE_TOP = 1
            val drawableRight = 2
            //val DRAWABLE_BOTTOM = 3

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= edtAddress.right - edtAddress.compoundDrawables[drawableRight].bounds.width()) {
                    // your action here
                    val addressStr = edtAddress.text.toString()
                    if (addressStr.isEmpty()) {
                        Toast.makeText(this@MainActivity, "Address field is empty", Toast.LENGTH_SHORT).show()
                        return@OnTouchListener false
                    }
                    getLocation(addressStr)

                    return@OnTouchListener true
                }
            }
            false
        })

    }

    override fun onMapReady(p0: GoogleMap?) {
        mapReady = true
        mMap = p0
    }

    @SuppressLint("SetTextI18n")
    private fun getLocation(addressStr: String) {
        mMap?.clear()
        val coder = Geocoder(applicationContext, Locale.getDefault())
        val address: List<Address>

        try {
            address = coder.getFromLocationName(addressStr, 5)
            if (address == null) {
                Toast.makeText(this, "No Location is found", Toast.LENGTH_LONG).show()
                Log.e("TAG", "address == null")
            }

            Log.e("TAG", " size ${address.size}")
            if (address != null && address.isNotEmpty()) {
                val location = address[0]
                val lat = location.latitude
                val lon = location.longitude
                txtCoordinates.text = "Latitude: $lat & Longitude: $lon"
                Log.e("TAG", "\"Latitude $lat and Longitude $lon\"")
                Toast.makeText(this, "Latitude $lat and Longitude $lon", Toast.LENGTH_LONG).show()

                marker = MarkerOptions()
                    .position(LatLng(lat, lon))
                    .title(addressStr)

                cameraPosition = CameraPosition.builder()
                    .target(LatLng(lat, lon))
                    .zoom(17f)
                    .bearing(0f)
                    .tilt(45f)
                    .build()

                if (mapReady) {
                    mMap?.addMarker(marker)
                    flyTo(cameraPosition!!)
                }

            } else {
                Toast.makeText(this, "address is null can show Location name is not correct", Toast.LENGTH_LONG).show()
                Log.e("TAG", " address is null")
            }

        } catch (e: IOException) {
            Log.e("TAG", "${e.message}")
            e.printStackTrace()
        }

    }

    private fun flyTo(target: CameraPosition) {
        mMap?.animateCamera(CameraUpdateFactory.newCameraPosition(target), 5000, null)
    }
}
