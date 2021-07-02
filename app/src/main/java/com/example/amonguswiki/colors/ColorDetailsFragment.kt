package com.example.amonguswiki.colors

import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.amonguswiki.R
import com.example.amonguswiki.navigator.Data
import com.example.amonguswiki.navigator.Navigator
import com.google.android.material.appbar.MaterialToolbar


class
ColorDetailsFragment : Fragment() {

    lateinit var tb_color_details:MaterialToolbar
    lateinit var tbcd_title: TextView
    lateinit var wv_color:  WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_color_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_color_details)
        tbcd_title.text = Data.color.name


        tb_color_details.setNavigationOnClickListener {
            Navigator.goBack()
        }

        wv_color.webChromeClient = object : WebChromeClient() {}

        wv_color.webViewClient = object : WebViewClient() {}

        val settings = wv_color.settings
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true

        wv_color.loadUrl(Data.color.html)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun startUp()  {

        tb_color_details=requireView().findViewById(R.id.tb_color_details)
        tbcd_title=requireView().findViewById(R.id.tbcd_title)
        wv_color=requireView().findViewById(R.id.wv_color)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_map -> {

                if (Data.color.fav) {

                    Data.color.fav = false
                    item.setIcon(R.drawable.heart)
                    Toast.makeText(activity, "Mapa quitado de favorito", Toast.LENGTH_SHORT).show()
                } else {

                    Data.color.fav = true
                    item.setIcon(R.drawable.heartfilled)
                    Toast.makeText(activity, "Mapa añadido a favorito", Toast.LENGTH_SHORT).show()

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}