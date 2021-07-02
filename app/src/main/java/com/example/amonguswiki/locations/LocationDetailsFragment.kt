package com.example.amonguswiki.locations

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


class LocationDetailsFragment : Fragment() {

    lateinit var tb_location_details: MaterialToolbar
    lateinit var tbld_title: TextView
    lateinit var wv_location: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_location_details)
        tbld_title.text = Data.location.name


        tb_location_details.setNavigationOnClickListener {
            Navigator.goBack()
        }

        wv_location.webChromeClient = object : WebChromeClient() {}

        wv_location.webViewClient = object : WebViewClient() {}

        val settings = wv_location.settings
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true

        wv_location.loadUrl(Data.location.html)
    }

    private fun startUp() {

        tb_location_details = requireView().findViewById(R.id.tb_location_details)
        tbld_title = requireView().findViewById(R.id.tbld_title)
        wv_location = requireView().findViewById(R.id.wv_location)

    }
        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_map -> {

                if (Data.location.fav) {

                    Data.location.fav = false
                    item.setIcon(R.drawable.heart)
                    Toast.makeText(activity, "Ubicación quitado de favorito", Toast.LENGTH_SHORT).show()
                } else {

                    Data.location.fav = true
                    item.setIcon(R.drawable.heartfilled)
                    Toast.makeText(activity, "Ubicación añadido a favorito", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }



}