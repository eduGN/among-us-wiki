package com.example.amonguswiki.navigator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.amonguswiki.R

object Navigator {
    var activity: FragmentActivity? = null

    fun init(activity: FragmentActivity?) {
        Navigator.activity = activity
    }

    fun toNavigateinHome(fragment: Fragment, addBackStack: Boolean = false) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, fragment)


        if (addBackStack) {
            transaction?.addToBackStack(fragment.toString())
        }

        transaction?.commit()
    }


    fun toNavigateinLogin(fragment: Fragment, addBackStack: Boolean = false) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.loginContainer, fragment)


        if (addBackStack) {
            transaction?.addToBackStack(fragment.toString())
        }

        transaction?.commit()
    }

    fun toNavigateHome(fragment: Fragment, addBackStack: Boolean = false) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.mainContainer, fragment)


        if (addBackStack) {
            transaction?.addToBackStack("")
        }

        transaction?.commit()
    }

    fun goBack() {
        activity?.supportFragmentManager?.popBackStack()
    }

}