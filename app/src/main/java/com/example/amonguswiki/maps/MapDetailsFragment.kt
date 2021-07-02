package com.example.amonguswiki.maps

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.amonguswiki.R
import com.example.amonguswiki.favorites.UserData
import com.example.amonguswiki.navigator.Data
import com.example.amonguswiki.navigator.Navigator
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MapDetailsFragment : Fragment() {

    private lateinit var menu: Menu
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var tb_map_details: MaterialToolbar
    lateinit var tbmd_title: TextView
    lateinit var wv_map: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        createFav()
        getFav()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_map_details)
        tbmd_title.text = Data.map.name


        tb_map_details.setNavigationOnClickListener {
            Navigator.goBack()
        }

        wv_map.webChromeClient = object : WebChromeClient() {}

        wv_map.webViewClient = object : WebViewClient() {}

        val settings = wv_map.settings
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true

        Data.map.html?.let { wv_map.loadUrl(it) }
        wv_map.reload()
    }

    private fun startUp()  {

        auth = Firebase.auth
        db = Firebase.firestore
        tb_map_details=requireView().findViewById(R.id.tb_map_details)
        tbmd_title=requireView().findViewById(R.id.tbmd_title)
        wv_map=requireView().findViewById(R.id.wv_map)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater)
        this.menu=menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_map -> {
                setFav(Data.map.fav!=true, item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setFav(fav: Boolean, item: MenuItem) {

        db.collection("users").document(auth.currentUser!!.uid).collection("maps")
            .document(Data.map.id)
            .update("favorite", fav).addOnSuccessListener {

                if(!fav){
                    Data.map.fav = false
                    item.setIcon(R.drawable.heart)
                    Toast.makeText(activity, "Mapa quitado de favorito", Toast.LENGTH_SHORT).show()
                } else {
                    Data.map.fav = true
                    item.setIcon(R.drawable.heartfilled)
                    Toast.makeText(activity, "Mapa añadido a favorito", Toast.LENGTH_SHORT).show()

                }
            }.addOnFailureListener {
                Log.e("miapp", it.toString())
                Log.e("miapp", it.message?:"")

            }
    }

    fun createFav(){

        val userData=UserData()

        db.collection("users").document(auth.currentUser!!.uid).collection("maps")
            .document(Data.map.id)
            .set(userData).addOnSuccessListener {
            }.addOnFailureListener {
                Log.e("miapp", it.toString())
                Log.e("miapp", it.message?:"")

            }
    }

    fun getFav(){

        db.collection("users").document(auth.currentUser!!.uid).collection("maps")
            .document(Data.map.id)
            .get().addOnSuccessListener {

                val user= it.toObject(UserData::class.java)
                if(user?.favorite==true){
                    menu.findItem(R.id.fav_map).setIcon(R.drawable.heart)
                }

            }.addOnFailureListener {
                Log.e("miapp", it.toString())
                Log.e("miapp", it.message?:"")

            }
    }
}


