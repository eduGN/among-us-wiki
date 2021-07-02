package com.example.amonguswiki.abilities

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


class AbilityDetailsFragment : Fragment() {

    lateinit var wv_ability: WebView
    lateinit var tb_ability_details: MaterialToolbar
    lateinit var tbad_title:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ability_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_ability_details)
        tbad_title.text = Data.ability.name


        tb_ability_details.setNavigationOnClickListener {
            Navigator.goBack()
        }

        wv_ability.webChromeClient = object : WebChromeClient() {}

        wv_ability.webViewClient = object : WebViewClient() {}

        val settings = wv_ability.settings
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true

        wv_ability.loadUrl(Data.ability.html)
    }

    private fun startUp() {

        wv_ability = requireView().findViewById(R.id.wv_ability)
        tb_ability_details = requireView().findViewById(R.id.tb_ability_details)
        tbad_title = requireView().findViewById(R.id.tbad_title)
    }

        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_map -> {

                if (Data.ability.fav) {

                    Data.ability.fav = false
                    item.setIcon(R.drawable.heart)
                    Toast.makeText(activity, "Habilidad quitado de favorito", Toast.LENGTH_SHORT).show()
                } else {

                    Data.ability.fav = true
                    item.setIcon(R.drawable.heartfilled)
                    Toast.makeText(activity, "Habilidad añadido a favorito", Toast.LENGTH_SHORT).show()

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}