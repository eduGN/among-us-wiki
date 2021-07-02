package com.example.amonguswiki.maps

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amonguswiki.R
import com.example.amonguswiki.navigator.Data
import com.example.amonguswiki.navigator.ItemDecorationColumns
import com.example.amonguswiki.navigator.Navigator
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random



class MapsFragment : Fragment() {

    lateinit var rv_maps: RecyclerView
    lateinit var tb_maps: MaterialToolbar
    lateinit var tb_mapsimg : ImageView
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    var allMaps: ArrayList<Map> = arrayListOf()
    lateinit var adapter: MapsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_maps)
        tb_maps.setNavigationOnClickListener {
            Navigator.goBack()
        }
        randomMap()
        uploadMaps()
        loadMaps()
         activity?.let {
             adapter = MapsAdapter(allMaps) {
                Data.map = it
                Navigator.toNavigateinHome(MapDetailsFragment(), true)
            }
        }

        rv_maps.addItemDecoration(
            ItemDecorationColumns(resources.getDimensionPixelSize(R.dimen.dp10))
        )
        rv_maps.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_maps.adapter = adapter

    }

    private fun startUp(){
        auth = Firebase.auth
        db = Firebase.firestore
        rv_maps=requireView().findViewById(R.id.rv_maps)
        tb_maps=requireView().findViewById(R.id.tb_maps)
        tb_mapsimg=requireView().findViewById(R.id.tb_mapsimg)
    }

    private fun loadMaps() {

        db.collection("maps").get().addOnSuccessListener { value ->

                allMaps.clear()
                for (doc in value!!) {
                    val map = doc.toObject(Map::class.java)
                    map.id=doc.id
                    map.name?.let { Log.i("miapp", it) }
                    Log.i("miapp", "Todo bien hasta aquí")
                    allMaps.add(map)
                }
            adapter.notifyDataSetChanged()

        }

    }

    private fun uploadMaps(){
        val newMap=Map()
        newMap.name="Polus"
        newMap.html="https://among-us-8a1d9.web.app/abilities/matar.html"
        newMap.icon="https://firebasestorage.googleapis.com/v0/b/among-us-8a1d9.appspot.com/o/img%2Fmaps%2Ficontheskeld.png?alt=media&token=edf7f835-b046-4c58-92fc-3f19617eb83e"
        newMap.fav=false
        newMap.img="https://firebasestorage.googleapis.com/v0/b/among-us-8a1d9.appspot.com/o/img%2Fmaps%2Fskeld.jpg?alt=media&token=cc51e39e-5344-49ff-9a05-6445d0262cf8"

        db.collection("maps").document().set(newMap)
    }


    fun randomMap(){

        val random = Random.nextInt(0, 3)

        val theskeld=R.drawable.theskeld
        val polus=R.drawable.polus
        val mirahq=R.drawable.mirahq
        var map=0

        if(random==0) map=theskeld
        if(random==1) map=polus
        if(random==2) map=mirahq

        tb_mapsimg.setImageResource(map)

    }
}