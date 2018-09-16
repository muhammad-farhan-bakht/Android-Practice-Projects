package example.farhan.com.listview_in_fragment.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import example.farhan.com.listview_in_fragment.R
import example.farhan.com.listview_in_fragment.adapters.ListViewAdapter

/**
 * A simple [Fragment] subclass.
 *
 */
class ListViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_list_view, container, false)

        val data: ArrayList<String> = arrayListOf()
        data.add("ABC")
        data.add("ABC")
        data.add("ABC")
        data.add("ABC")
        data.add("ABC")

        val listView: ListView = view.findViewById(R.id.list_view_root)
        val mAdapter = ListViewAdapter(activity!!, data)

        listView.adapter = mAdapter

        return view
    }
}
