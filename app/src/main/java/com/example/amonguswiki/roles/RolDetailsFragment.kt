package com.example.amonguswiki.roles

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

class RolDetailsFragment : Fragment() {

    lateinit var tb_rol_details: MaterialToolbar
    lateinit var tbrd_title: TextView
    lateinit var wv_rol: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rol_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_rol_details)
        tbrd_title.text = Data.rol.name


        tb_rol_details.setNavigationOnClickListener {
            Navigator.goBack()
        }

        wv_rol.webChromeClient = object : WebChromeClient() {}

        wv_rol.webViewClient = object : WebViewClient() {}

        val settings = wv_rol.settings
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true

        wv_rol.loadUrl(Data.rol.html)
    }

    private fun startUp()  {

        tb_rol_details=requireView().findViewById(R.id.tb_rol_details)
        tbrd_title=requireView().findViewById(R.id.tbrd_title)
        wv_rol=requireView().findViewById(R.id.wv_rol)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_map -> {

                if (Data.rol.fav) {

                    Data.rol.fav = false
                    item.setIcon(R.drawable.heart)
                    Toast.makeText(activity, "Rol quitado de favorito", Toast.LENGTH_SHORT).show()
                } else {

                    Data.rol.fav = true
                    item.setIcon(R.drawable.heartfilled)
                    Toast.makeText(activity, "Rol añadido a favorito", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}