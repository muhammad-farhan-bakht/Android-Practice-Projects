package example.farhan.com.listview_in_fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import example.farhan.com.listview_in_fragment.fragments.ListViewFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.root_frame, ListViewFragment())
                .commit()
    }
}
