package com.example.amonguswiki.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.amonguswiki.R
import com.example.amonguswiki.colors.ColorsFragment
import com.example.amonguswiki.home.HomeFragment
import com.example.amonguswiki.login.User
import com.example.amonguswiki.navigator.Navigator
import com.example.amonguswiki.notifications.NotificationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Navigator.init(this)
        startUpFirebase()
        showName()
        Navigator.toNavigateinHome(HomeFragment())
        val bottom_nv=findViewById<BottomNavigationView>(R.id.bottom_nv)

        bottom_nv.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bn_home -> {
                    Navigator.toNavigateinHome(HomeFragment(), true)
                    true
                }
                R.id.bn_fav -> {
                    Navigator.toNavigateinHome(ColorsFragment(), true)
                    true
                }
                R.id.bn_recents -> {
                    Navigator.toNavigateinHome(HomeFragment(), true)
                    true
                }
                R.id.bn_noti -> {
                    Navigator.toNavigateinHome(NotificationsFragment(), true)
                    true
                }
                else -> false
            }
        }
        bottom_nv.selectedItemId = R.id.bn_home
    }

    private fun startUpFirebase()  {
        auth = Firebase.auth
        db = Firebase.firestore
    }

    fun showName() {
        val uid = auth.currentUser!!.uid

        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                Toast.makeText(this, "Bienvenido!! ${user?.name}", Toast.LENGTH_LONG).show()
                //Snackbar.make(window.decorView.rootView, "Bienvenido!! ${user?.name} ", Snackbar.LENGTH_LONG).show()
            }.addOnFailureListener { exception ->
                Log.w("miapp", "Error getting documents.", exception)
            }
    }
}
