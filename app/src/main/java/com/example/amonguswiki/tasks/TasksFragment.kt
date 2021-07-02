package com.example.amonguswiki.tasks

import TasksAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amonguswiki.R
import com.example.amonguswiki.favorites.Article
import com.example.amonguswiki.navigator.Data
import com.example.amonguswiki.navigator.ItemDecorationColumns
import com.example.amonguswiki.navigator.Navigator
import com.google.android.material.appbar.MaterialToolbar


class TasksFragment : Fragment() {

    lateinit var tb_tasks:MaterialToolbar
    lateinit var  rv_tasks: RecyclerView
    lateinit var wv_task_index: WebView
    lateinit var scrollmaps: NestedScrollView

    var scroll:Int=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_tasks)
        tb_tasks.setNavigationOnClickListener {
            Navigator.goBack()
        }
        val data = generateTasks()
        val adapter = activity?.let {
            TasksAdapter(data) {
                Data.task = it
                Navigator.toNavigateinHome(TaskDetailsFragment(), true)
            }
        }

        rv_tasks.addItemDecoration(
            ItemDecorationColumns(resources.getDimensionPixelSize(R.dimen.dp10))
        )
        rv_tasks.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_tasks.adapter = adapter

        wv_task_index.webChromeClient = object : WebChromeClient() {}

        wv_task_index.webViewClient = object : WebViewClient() {}

        val settings = wv_task_index.settings
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true

        wv_task_index.loadUrl("file:///android_asset/src/tasks/index.html")
    }

    fun startUp() {

        tb_tasks=requireView().findViewById(R.id.tb_tasks)
        rv_tasks=requireView().findViewById(R.id.rv_tasks)
        wv_task_index=requireView().findViewById(R.id.wv_task_index)
        scrollmaps=requireView().findViewById(R.id.scrollmaps)
    }

    override fun onResume() {
        super.onResume()
        scrollmaps.scrollBy(0,scroll+200)
    }

    override fun onPause() {

        super.onPause()
        Log.d("TasksFragment", scrollmaps.scrollY.toString())
        scroll=scrollmaps.scrollY
    }



    fun generateTasks(): List<Article> {

        val task1= Article("Alinear salida de Motor", "file:///android_asset/src/tasks/tarea1.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/9/9e/AlignEngineOutput.jpg/revision/latest?cb=20200917211103")

        val task2= Article("Aliear Telescopio", "file:///android_asset/src/tasks/tarea2.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/c/c9/Among-us-telescope.png/revision/latest?cb=20200923222916")

        val task3= Article("Armar Artefacto", "file:///android_asset/src/tasks/tarea3.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/b/b5/AssembleArtifact.png/revision/latest?cb=20200917143521")

        val task4= Article("Comprar Bebida", "file:///android_asset/src/tasks/tarea4.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/0/05/Beverage.png/revision/latest?cb=20200912131838")

        val task5= Article("Calibrar Distribuidor", "file:///android_asset/src/tasks/tarea5.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/2/28/CalibrateDistributor.jpg/revision/latest?cb=20200917210646")

        val task6= Article("Mapa de Navegación", "file:///android_asset/src/tasks/tarea6.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/8/87/ChartCourse.jpg/revision/latest?cb=20200915190323")

        val task7= Article("Limpiar Filtro O2", "file:///android_asset/src/tasks/tarea7.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/b/b8/Clean_O2_Filter_Task.png/revision/latest?cb=20200914023829")

        val task8= Article("Destruir Asteroides", "file:///android_asset/src/tasks/tarea8.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/2/2f/Clear_Asteroids_%28The_Skeld%29_-_Among_Us_Task/revision/latest?cb=20201118180003")

        val task9= Article("Desviar Energía", "file:///android_asset/src/tasks/tarea9.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/d/d6/Divert_Power_Panel.PNG/revision/latest?cb=20200922142830")

        val task10= Article("Vaciar Basurero", "file:///android_asset/src/tasks/tarea10.html",
            "https://static.wikia.nocookie.net/among-us-wiki/images/9/90/Among_us_garbage_disposal.png/revision/latest?cb=20201109133557")

        return listOf(task1,task2,task3,task4,task5,task6, task7, task8, task9, task10)
    }

}