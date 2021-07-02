package com.example.amonguswiki.colors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amonguswiki.R
import com.example.amonguswiki.navigator.Data
import com.example.amonguswiki.navigator.ItemDecorationColumns
import com.example.amonguswiki.navigator.Navigator
import com.google.android.material.appbar.MaterialToolbar

class ColorsFragment : Fragment() {
    lateinit var rv_colors: RecyclerView
    lateinit var tb_colors: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_colors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_colors)
        tb_colors.setNavigationOnClickListener {
            Navigator.goBack()
        }
        val data = Color.generateColors()
        val adapter = activity?.let {
            ColorsAdapter(data) {
                Data.color = it
                Navigator.toNavigateinHome(ColorDetailsFragment(), true)
            }
        }

        rv_colors.addItemDecoration(
            ItemDecorationColumns(resources.getDimensionPixelSize(R.dimen.dp10))
        )
        rv_colors.layoutManager =
            GridLayoutManager(activity, 2)
        rv_colors.adapter = adapter

    }

    private fun startUp(){

        rv_colors=requireView().findViewById(R.id.rv_colors)
        tb_colors=requireView().findViewById(R.id.tb_colors)
    }

}