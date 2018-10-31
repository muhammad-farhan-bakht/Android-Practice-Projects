package example.farhan.com.practicemapsearchbyplacename

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val coder = Geocoder(this)
        val address: List<Address>
        try {

            address = coder.getFromLocationName("gulshan", 5)
            if (address == null) {
                Toast.makeText(this, "No Location is found", Toast.LENGTH_LONG).show()
                Log.e("TAG", "address == null")
            }

            Log.e("TAG", " size ${address.size}")
            val location = address[0]

            val lat = location.latitude
            val lon = location.longitude

            Log.e("TAG", "\"Latitude $lat and Longitude $lon\"")
            Toast.makeText(this, "Latitude $lat and Longitude $lon", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
