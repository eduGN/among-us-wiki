package com.example.amonguswiki.roles

import RolesAdapter
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


class RolesFragment : Fragment() {

    lateinit var rv_roles: RecyclerView
    lateinit var tb_roles: MaterialToolbar



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_roles)
        tb_roles.setNavigationOnClickListener {
            Navigator.goBack()
        }
        val data = generateRoles()
        val adapter = activity?.let {
            RolesAdapter(data) {
                Data.rol = it
                Navigator.toNavigateinHome(RolDetailsFragment(), true)
            }
        }

        rv_roles.addItemDecoration(
            ItemDecorationColumns(resources.getDimensionPixelSize(R.dimen.dp10))
        )
        rv_roles.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_roles.adapter = adapter

    }

    private fun startUp(){

        rv_roles=requireView().findViewById(R.id.rv_roles)
        tb_roles=requireView().findViewById(R.id.tb_roles)
    }



    fun generateRoles(): List<Article> {

        val crewmate=Article("Tripulante", "file:///android_asset/src/roles/tripulante.html",
            "https://allgamersin.com/wp-content/uploads/2020/09/among-us-portada-1068x600.jpg")

        val impostor=Article("Impostor", "file:///android_asset/src/roles/impostor.html",
        "https://larepublica.pe/resizer/Ktzh9HwFNLcpEY7fsbPJo3mWIP4=/1250x735/top/smart/cloudfront-us-east-1.images.arcpublishing.com/gruporepublica/GNHUW5IA7RFG7M26OEMRHI5PQA.png")

        return listOf<Article>(crewmate, impostor)
    }
}