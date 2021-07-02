package com.example.amonguswiki.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.amonguswiki.R
import com.example.amonguswiki.abilities.AbilitiesFragment
import com.example.amonguswiki.activities.MainActivity
import com.example.amonguswiki.colors.ColorsFragment
import com.example.amonguswiki.locations.LocationsFragment
import com.example.amonguswiki.login.User
import com.example.amonguswiki.maps.MapsFragment
import com.example.amonguswiki.navigator.Navigator
import com.example.amonguswiki.roles.RolesFragment
import com.example.amonguswiki.sounds.SoundDetailsFragment
import com.example.amonguswiki.tasks.TasksFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var tb_mainhome: MaterialToolbar
    lateinit var drawer_layout: DrawerLayout
    lateinit var navView: NavigationView
    val TAG="home"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_mainhome)
        toggle = ActionBarDrawerToggle(activity, drawer_layout, tb_mainhome, 0, 0)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        initHeader()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient= activity?.let { GoogleSignIn.getClient(it,gso) }!!

    }

    private fun startUp()  {
        auth = Firebase.auth
        db = Firebase.firestore

        tb_mainhome= requireView().findViewById(R.id.tb_mainhome)
        drawer_layout= requireView().findViewById(R.id.drawer_layout)
        navView = requireView().findViewById(R.id.nav_view)
    }


    private fun initHeader() {

        val nav_view=view?.findViewById<NavigationView>(R.id.nav_view)
        val uid = auth.currentUser!!.uid

        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                val view=nav_view?.getHeaderView(0)

                view?.findViewById<TextView>(R.id.header_name)?.text=user?.name
                view?.findViewById<TextView>(R.id.header_email)?.text=user?.email

                if(user?.photoUrl.isNullOrEmpty()){
                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/among-us-wiki.appspot.com/o/adminmirahg.png?alt=media&token=2b13b50e-28f6-4ff9-877e-37d57cf03370").transform(
                        CircleTransform()
                    ).into(view?.findViewById(R.id.profileImage))
                } else {
                    Picasso.get().load(user?.photoUrl).transform(
                        CircleTransform()
                    ).into(view?.findViewById(R.id.profileImage))
                }
            }.addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }

    fun exitUser(){
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }
        startActivity(Intent(activity, MainActivity::class.java))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_maps -> {
                Toast.makeText(activity, "Messages clicked", Toast.LENGTH_SHORT).show()
                Navigator.toNavigateinHome(MapsFragment(),  true)

            }
            R.id.nav_tasks -> {

                Toast.makeText(activity, "Messages clicked", Toast.LENGTH_SHORT).show()
                Navigator.toNavigateinHome(TasksFragment(), true)

            }
            R.id.nav_roles -> {
                Toast.makeText(activity, "Messages clicked", Toast.LENGTH_SHORT).show()
                Navigator.toNavigateinHome(RolesFragment(), true)
            }

            R.id.nav_rooms -> {
                Toast.makeText(activity, "Messages clicked", Toast.LENGTH_SHORT).show()
                Navigator.toNavigateinHome(LocationsFragment(), true)
            }

            R.id.nav_colors -> {
                Toast.makeText(activity, "Colores", Toast.LENGTH_SHORT).show()
                Navigator.toNavigateinHome(ColorsFragment(), true)
                //Navigator.toNavigate(ColorsFragment())
            }

            R.id.nav_abilities -> {
                Toast.makeText(activity, "Habilidades", Toast.LENGTH_SHORT).show()
                Navigator.toNavigateinHome(AbilitiesFragment(), true)
            }

            R.id.nav_hacks -> {
                Toast.makeText(activity, "Messages clicked", Toast.LENGTH_SHORT).show()
                //Navigator.toNavigate(HacksFragment())
            }

            R.id.nav_contact -> {
                Toast.makeText(activity, "Messages clicked", Toast.LENGTH_SHORT).show()
                Navigator.toNavigateinHome(SoundDetailsFragment())
            }

            R.id.nav_about -> {
                Toast.makeText(activity, "Messages clicked", Toast.LENGTH_SHORT).show()
                //Navigator.toNavigate(AboutFragment())
            }

            R.id.nav_logout -> {
                item.actionView.findViewById<LinearLayout>(R.id.logout_footer).setOnClickListener {
                   exitUser()
                }
            }

            else -> {
                Toast.makeText(activity, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}