package com.example.amonguswiki.login

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.amonguswiki.R
import com.example.amonguswiki.activities.HomeActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class RegisterFragment : Fragment() {
    var dataValid: Boolean = false
    var userValid: Boolean = false
    var passValid: Boolean = false
    var emailValid: Boolean = false;
    var confirmPassValid: Boolean = false
    var user: String? = ""
    var pass: String? = ""
    var confirmPass: String? = ""
    var mail: String? = ""
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    val TAG="miapp"
    lateinit var checkbox_conditions: CheckBox
    lateinit var username_r: TextInputEditText
    lateinit var email_r: TextInputEditText
    lateinit var password_r: TextInputEditText
    lateinit var confirm_password: TextInputEditText
    lateinit var birthday: TextInputEditText
    lateinit var terms_conditions:TextView
    lateinit var register:Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        terms_conditions.movementMethod = LinkMovementMethod.getInstance()
        enableButton(false)
        username_r.doAfterTextChanged {
            user = username_r.text.toString()
            validUser(user)
            validRegister(userValid, passValid, emailValid, confirmPassValid,isDateValid(), isCheckbox())
            enableButton(dataValid)
            changetextBtncolor(dataValid)
        }

        password_r.doAfterTextChanged {
            pass = password_r.text.toString()
            validPass(pass)
            validRegister(userValid, passValid, emailValid, confirmPassValid,isDateValid(), isCheckbox())
            enableButton(dataValid)
            changetextBtncolor(dataValid)
        }

        email_r.doAfterTextChanged {
            mail = email_r.text.toString()
            validEmail(mail)
            validRegister(userValid, passValid, emailValid, confirmPassValid,isDateValid(), isCheckbox())
            enableButton(dataValid)
            changetextBtncolor(dataValid)
        }

        confirm_password.doAfterTextChanged {

            confirmPass = confirm_password.text.toString()
            validConfirmPass(confirmPass, pass)
            validRegister(userValid, passValid, emailValid, confirmPassValid,isDateValid(), isCheckbox())
            enableButton(dataValid)
            changetextBtncolor(dataValid)
        }

        checkbox_conditions.setOnClickListener{
            validRegister(userValid, passValid, emailValid, confirmPassValid,isDateValid(), isCheckbox())
            enableButton(dataValid)
            changetextBtncolor(dataValid)
        }


        birthday.setOnClickListener {
            showDatePickerDialog()
            validRegister(userValid, passValid, emailValid, confirmPassValid,isDateValid(), isCheckbox())
            enableButton(dataValid)
            changetextBtncolor(dataValid)
        }

        register.setOnClickListener {
            registerUser(mail.toString(), pass.toString(), user.toString(), birthday.text.toString())
        }
    }

     private fun startUp(){

         auth = Firebase.auth
         db = Firebase.firestore
         checkbox_conditions=requireView().findViewById(R.id.checkbox_conditions)
         username_r=requireView().findViewById(R.id.username_r)
         email_r=requireView().findViewById(R.id.email_r)
         password_r=requireView().findViewById(R.id.password_r)
         confirm_password=requireView().findViewById(R.id.confirm_password)
         birthday=requireView().findViewById(R.id.birthday)
         terms_conditions=requireView().findViewById(R.id.terms_conditions)
         register=requireView().findViewById(R.id.register)

    }


    private fun enableButton(bool: Boolean) {
        register.isEnabled = bool
        register.alpha = if (bool) 1f else 0.8f
    }

    private fun changetextBtncolor(bool: Boolean)  {

        if (bool) {
            context?.let { ContextCompat.getColor(it, R.color.colorPrimaryDark2) }?.let {
                register.setTextColor(it)
            }

            context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }?.let {
                register.setBackgroundColor(it)
            } //sendEmail.setTextColor(resources.getColor(R.color.colorPrimaryDark2))
        } else{
            context?.let { ContextCompat.getColor(it, R.color.loading) }?.let {
                register.setTextColor(it)
            }

            context?.let { ContextCompat.getColor(it, R.color.colorPrimaryDark2) }?.let {
                register.setBackgroundColor(it)
            }
        }
    }

    private fun isUsernameValid(user: String?): Boolean {

        val userREGEX = Pattern.compile(
            "^"+"(?=\\S+$)"+"(?=.*[a-zA-Z])"+".{3,}"+"$"
        )
        return if (user == null) false
        else return userREGEX.matcher(user).matches()
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
                    /*"(?=.*[@#$%^&+=€])" +    //at least 1 special character*/
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$"
        );

        return if (pass == null) false
        else return passwordREGEX.matcher(pass).matches()
    }

    private fun validUser(user: String?) {

        if (!isUsernameValid(user)) {
            username_r.error = "Introduce un nombre de al menos 3 carácteres y sin espacios"
            userValid = false
        } else {
            userValid = true
        }
    }

    private fun validPass(pass: String?) {

        if (!isPasswordValid(pass)) {
            password_r.error =
                "La contraseña debe contener al menos 6 carácteres además de una minúscula, mayúscula y número"
            passValid = false
        } else {
            passValid = true
        }
    }

    private fun validEmail(e_mail: String?) {

        if (!isEmailValid(e_mail)) {
            email_r.error = "Introduce un email válido"
            emailValid = false
        } else {
            emailValid = true
        }
    }

    private fun validConfirmPass(confirmPass: String?, pass: String?) {

        if (isPasswordValid(pass) && confirmPass == pass) {
            confirmPassValid = true
        } else {
            confirm_password.error = "Las contraseñas no coinciden"
            confirmPassValid = false
        }
    }

    private fun isCheckbox(): Boolean {

        return checkbox_conditions.isChecked
    }

    private fun validRegister(
        userValid: Boolean,
        passValid: Boolean,
        emailValid: Boolean,
        confirmPassValid: Boolean,
        date : Boolean,
        checkBox: Boolean

    ) {
        dataValid = userValid && passValid && emailValid && confirmPassValid && date && checkBox
    }

    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                // +1 because January is zero
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                birthday.setText(selectedDate)
            })

        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun isDateValid():Boolean{
        return !birthday.text.toString().isBlank()
    }

    fun navigateToHome() {
        startActivity(Intent(activity, HomeActivity::class.java))
    }

    fun createUser(uid: String,email: String, name: String, birthday: String) {
        val newUser = User()
        newUser.name= name
        newUser.birthday=birthday
        newUser.email=email
        db.collection("users").document(uid).set(newUser).addOnSuccessListener {
            navigateToHome()
        }.addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
    }

    fun registerUser(email: String, password: String, name: String, birthday: String) {

        activity?.let {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(
                            activity, "createUserWithEmail:success",
                            Toast.LENGTH_SHORT
                        ).show()
                        val user = auth.currentUser
                        createUser(user?.uid.toString(),email, name, birthday)
                        //navigateToHome()
                       // Log.d(TAG, "${user?.uid}")
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            activity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

}