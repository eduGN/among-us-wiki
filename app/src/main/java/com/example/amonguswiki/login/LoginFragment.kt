package com.example.amonguswiki.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.amonguswiki.R
import com.example.amonguswiki.activities.HomeActivity
import com.example.amonguswiki.navigator.Navigator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


@Suppress("NAME_SHADOWING")
class LoginFragment : Fragment() {
    var dataValid: Boolean = false
    var mailValid: Boolean = false
    var passValid: Boolean = false
    var mail: String? = ""
    var pass: String? = ""
    private lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var googlelogin: MaterialCardView
    lateinit var rememberPass: Button
    lateinit var signin: Button
    lateinit var loginEmailPass: Button
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText

    val RC_SIGN_IN = 444
    val TAG = "miapp"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         startUp()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }!!

        googlelogin.setOnClickListener {
            signInGoogle()
        }

        rememberPass.setOnClickListener {
            forgottenPass()
         Navigator.toNavigateinHome(RememberPassFragment())
        }
        signin.setOnClickListener {
            signIn()
            Navigator.toNavigateinHome(RegisterFragment())


        }
        loginEmailPass.setOnClickListener {
            validLogin()
            if (dataValid) {
                loginrEmailPass(mail.toString(), pass.toString())
            } else if(!mailValid) { email.error="El email no es válido" }
                 else if(!passValid) { password.error="La contraseña no es válida" }
        }
        email.onFocusChangeListener = View.OnFocusChangeListener { _, _ -> email.error = null }
        password.onFocusChangeListener = View.OnFocusChangeListener { _, _ -> password.error = null }
    }

    private fun startUp()  {
        auth = Firebase.auth
        db = Firebase.firestore

        googlelogin=requireView().findViewById(R.id.googlelogin)
        rememberPass=requireView().findViewById(R.id.rememberPass)
        signin=requireView().findViewById(R.id.signin)
        loginEmailPass=requireView().findViewById(R.id.btn_login)
        email=requireView().findViewById(R.id.email)
        password=requireView().findViewById(R.id.password)
    }

    private fun forgottenPass() {
        Toast.makeText(activity, "Recordar contraseña", Toast.LENGTH_LONG).show()
    }

    private fun signIn() {
        Toast.makeText(activity, "Registro iniciado", Toast.LENGTH_LONG).show()
    }

    private fun isEmailValid(email: String?): Boolean {

        return if (email == null) false
        else Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(pass: String?): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 8 characters
                    "$"
        );

        return if (pass == null) false
        else return passwordREGEX.matcher(pass).matches()
    }

    private fun validEmail(mail: String?) {
        mailValid = isEmailValid(mail)
    }

    private fun validPass(pass: String?) {
        passValid = isPasswordValid(pass)
    }

    private fun validLogin() {

        mail = email.text.toString()
        validEmail(mail)
        pass = password.text.toString()
        validPass(pass)
        dataValid = mailValid && passValid
    }

    fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!, account.givenName, account.familyName, account.email, account.photoUrl.toString())
            } catch (error: ApiException) {
                Log.w(TAG, "Google sign in failed", error)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String, firstName: String?, lastName: String?, email: String?, photoUrl: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        activity?.let {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        createUser(user?.uid.toString(), firstName.toString(), lastName.toString(), email.toString(), photoUrl.toString())
                        navigateToHome()

                    } else {
                        view?.let { it1 -> Snackbar.make(it1, "Authentication Failed.", Snackbar.LENGTH_SHORT).show() }
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToHome()
        } else {
            Log.v(TAG, "Usuario actual ${currentUser?.uid}")
        }
    }

    fun navigateToHome() {
        startActivity(Intent(activity, HomeActivity::class.java))
    }

    fun createUser(uid: String, firstName: String, lastName: String, email: String, photoUrl: String) {
        val newUser = User()
        newUser.name= firstName
        newUser.lastname = lastName
        newUser.email=email
        newUser.photoUrl=photoUrl

        db.collection("users").document(uid).set(newUser).addOnSuccessListener {
            navigateToHome()

        }.addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
    }

    private fun loginrEmailPass(email: String, password: String) {

        activity?.let {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        navigateToHome()
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            activity, "Usuario o contraseña incorrecto",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}