package example.farhan.com.slider_demo

import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import me.relex.circleindicator.CircleIndicator
import java.util.*

class MainActivity : AppCompatActivity() {

    private var data: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data = arrayListOf()
        data?.add("https://via.placeholder.com/350x150")
        data?.add("https://via.placeholder.com/350x150")
        data?.add("https://via.placeholder.com/350x150")
        data?.add("https://via.placeholder.com/350x150")

        Log.e("TAg","data ${data?.size}")
        initImageSlider()
    }

    private fun initImageSlider() {
        val mPager = findViewById<ViewPager>(R.id.pager)
        val indicator = findViewById<View>(R.id.indicator) as CircleIndicator

        mPager.adapter = ImageSliderAdapter(this@MainActivity, data!!)
        indicator.setViewPager(mPager)

        var slideshowCurrentImage = 0

        // Auto start of viewpager
        val handler = Handler()

        val update = Runnable {

            if (slideshowCurrentImage == data?.size) { // images array length
                slideshowCurrentImage = 0
            }
            mPager.setCurrentItem(slideshowCurrentImage++, true)
        }

        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 2000, 2000)
    }
}
