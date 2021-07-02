package com.example.amonguswiki.locations

import LocationsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amonguswiki.R
import com.example.amonguswiki.favorites.Article
import com.example.amonguswiki.navigator.Data
import com.example.amonguswiki.navigator.ItemDecorationColumns
import com.example.amonguswiki.navigator.Navigator
import com.google.android.material.appbar.MaterialToolbar


class LocationsFragment : Fragment() {

    lateinit var tb_locations: MaterialToolbar
    lateinit var rv_locations: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_locations)
        tb_locations.setNavigationOnClickListener {
            Navigator.goBack()
        }
        val data = generateLocations()
        val adapter = activity?.let {
            LocationsAdapter(data) {
                Data.location = it
                Navigator.toNavigateinHome(LocationDetailsFragment(), true)
            }
        }

        rv_locations.addItemDecoration(
            ItemDecorationColumns(resources.getDimensionPixelSize(R.dimen.dp10))
        )
        rv_locations.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_locations.adapter = adapter

    }

    private fun startUp(){

        rv_locations=requireView().findViewById(R.id.rv_locations)
        tb_locations=requireView().findViewById(R.id.tb_locations)
    }


    fun generateLocations(): List<Article> {

        val task1= Article("Ala Médica", "file:///android_asset/src/colors/azul.html",
            "https://allgamersin.com/wp-content/uploads/2020/09/among-us-portada-1068x600.jpg")
        val task2= Article("Cafetería", "file:///android_asset/src/colors/azul.html",
            "https://allgamersin.com/wp-content/uploads/2020/09/among-us-portada-1068x600.jpg")
        val task3= Article("Navegación", "file:///android_asset/src/colors/azul.html",
            "https://allgamersin.com/wp-content/uploads/2020/09/among-us-portada-1068x600.jpg")
        val task4= Article("Reactor Telescopio", "file:///android_asset/src/colors/azul.html",
            "https://allgamersin.com/wp-content/uploads/2020/09/among-us-portada-1068x600.jpg")
        val task5= Article("Motor Superior Muestras", "file:///android_asset/src/colors/azul.html",
            "https://allgamersin.com/wp-content/uploads/2020/09/among-us-portada-1068x600.jpg")
        val task6= Article("Almacén", "file:///android_asset/src/colors/azul.html",
            "https://allgamersin.com/wp-content/uploads/2020/09/among-us-portada-1068x600.jpg")


        return listOf<Article>(task1,task2,task3,task4,task5,task6)
    }


}