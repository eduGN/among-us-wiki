package com.example.amonguswiki.abilities

import AbilitiesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amonguswiki.R
import com.example.amonguswiki.favorites.Article
import com.example.amonguswiki.navigator.Data
import com.example.amonguswiki.navigator.ItemDecorationColumns
import com.example.amonguswiki.navigator.Navigator
import com.google.android.material.appbar.MaterialToolbar

class AbilitiesFragment : Fragment() {
    lateinit var rv_abilities: RecyclerView
    lateinit var tb_abilities: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_abilities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_abilities)
        tb_abilities.setNavigationOnClickListener {
            Navigator.goBack()
        }
        val data = generateAbilities()
        val adapter = activity?.let {
            AbilitiesAdapter(data) {
                Data.ability = it
                Navigator.toNavigateinHome(AbilityDetailsFragment(), true)
            }
        }

        rv_abilities.addItemDecoration(
            ItemDecorationColumns(resources.getDimensionPixelSize(R.dimen.dp10))
        )
        rv_abilities.layoutManager =
            GridLayoutManager(activity, 2)
        rv_abilities.adapter = adapter

    }

    private fun startUp(){

        rv_abilities=requireView().findViewById(R.id.rv_abilities)
        tb_abilities=requireView().findViewById(R.id.tb_abilities)
    }

    fun generateAbilities(): List<Article> {

        val ability1= Article("Matar", "file:///android_asset/src/abilities/matar.html",
            "https://i.redd.it/m40yfb9cewr51.png")

        val ability2= Article("Sabotaje", "file:///android_asset/src/abilities/sabotaje.html",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXv7ZNEP56N5qOVD0CX9JDj6YPh6ToCvCrSA&usqp=CAU")

        val ability3= Article("Reportar", "file:///android_asset/src/abilities/reportar.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/9/94/Report.png/revision/latest/scale-to-width-down/112?cb=20201016191700")

        val ability4= Article("Ventilación", "file:///android_asset/src/abilities/vent.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/d/db/Vent.png/revision/latest/scale-to-width-down/116?cb=20200915213612")

        val ability5= Article("Usar", "file:///android_asset/src/abilities/usar.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/1/15/Use.png/revision/latest?cb=20201002163154")

        val ability6= Article("Admin","file:///android_asset/src/abilities/admin.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/b/b1/AdminButton.png/revision/latest?cb=20201016202836")

        val ability7= Article("Doorlog", "file:///android_asset/src/abilities/doorlog.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/b/b9/Doorlog.png/revision/latest?cb=20201002155519")

        val ability8= Article("Vitales", "file:///android_asset/src/abilities/vitales.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/1/1b/Vitals.png/revision/latest?cb=20201002162114")

        val ability9= Article("Customize", "file:///android_asset/src/abilities/customize.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/d/de/Customize.png/revision/latest?cb=20201030180447")

        val ability10= Article("Seguridad", "file:///android_asset/src/abilities/seguridad.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/2/2f/SecurityButton.png/revision/latest?cb=20201016202545")

        val ability11= Article("Emergencia", "file:///android_asset/src/abilities/emergency.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/1/18/Emergency_Meeting.png/revision/latest?cb=20200803160022")

        return listOf<Article>(ability1,ability2,ability3,ability4,ability5,ability6,ability7,ability8,ability9,ability10,ability11)
    }


}