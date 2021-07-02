package com.example.amonguswiki.tasks

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.amonguswiki.R
import com.example.amonguswiki.navigator.Data
import com.example.amonguswiki.navigator.Navigator
import com.google.android.material.appbar.MaterialToolbar


class TaskDetailsFragment : Fragment() {

    lateinit var tb_task_details: MaterialToolbar
    lateinit var tbtd_title: TextView
    lateinit var wv_task: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_task_details)
        tbtd_title.text = Data.task.name


        tb_task_details.setNavigationOnClickListener {
            Navigator.goBack()
        }

        wv_task.webChromeClient = object : WebChromeClient() {}

        wv_task.webViewClient = object : WebViewClient() {}

        val settings = wv_task.settings
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true

        wv_task.loadUrl(Data.task.html)
    }

    private fun startUp() {

        tb_task_details=requireView().findViewById(R.id.tb_task_details)
        tbtd_title=requireView().findViewById(R.id.tbtd_title)
        wv_task=requireView().findViewById(R.id.wv_task)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_map -> {

                if (Data.task.fav) {

                    Data.task.fav = false
                    item.setIcon(R.drawable.heart)
                    Toast.makeText(activity, "Tarea quitado de favorito", Toast.LENGTH_SHORT).show()
                } else {

                    Data.task.fav = true
                    item.setIcon(R.drawable.heartfilled)
                    Toast.makeText(activity, "Tarea añadido a favorito", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}