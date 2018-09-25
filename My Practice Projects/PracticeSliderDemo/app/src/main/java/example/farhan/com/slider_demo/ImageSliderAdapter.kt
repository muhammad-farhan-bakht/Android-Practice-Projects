package example.farhan.com.slider_demo

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageSliderAdapter(private var context: Context, var data: List<String>) : PagerAdapter() {

    override fun isViewFromObject(view: View, object1: Any): Boolean {

        return view == object1
    }

    override fun getCount(): Int {

        return if (data.isNotEmpty()) {
            data.size
        } else {
            0
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val myImageLayout = LayoutInflater.from(context).inflate(R.layout.slide_card, container, false)

        val myImage = myImageLayout.findViewById(R.id.image_to_slide) as ImageView
        // load the image by getting the url
        if (data.isNotEmpty()) {
            Log.e("TAg","data ${data[position]}")
            Glide.with(context).load(data[position]).into(myImage)
        } else {
            Glide.with(context).load(R.drawable.ic_launcher_background).into(myImage)
        }
        container.addView(myImageLayout, 0)

        return myImageLayout
    }
}