package com.example.amonguswiki.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amonguswiki.R
import com.example.amonguswiki.login.LoginFragment
import com.example.amonguswiki.navigator.Navigator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Navigator.init(this)
        Navigator.toNavigateinLogin(LoginFragment())
    }

}