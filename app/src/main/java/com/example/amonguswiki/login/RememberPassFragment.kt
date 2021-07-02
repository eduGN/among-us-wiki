package com.example.amonguswiki.login

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.amonguswiki.R
import com.google.android.material.textfield.TextInputEditText


class RememberPassFragment : Fragment() {

    var emailValid: Boolean = false;
    var mail: String? = ""
    lateinit var email_rp: TextInputEditText
    lateinit var sendEmail: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_remember_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startUp()
        enableButton(false)

        email_rp.doAfterTextChanged {

            mail = email_rp.text.toString()
            validEmail(mail)
            enableButton(emailValid)
            changetextBtncolor(emailValid)
        }

        sendEmail.setOnClickListener {
             succeedResetPass()
        }
    }

    private fun startUp(){

        email_rp=requireView().findViewById(R.id.email_rp)
        sendEmail=requireView().findViewById(R.id.sendEmail)
    }

    private fun enableButton(bool: Boolean) {
        sendEmail.isEnabled = bool
        sendEmail.alpha = if (bool) 1f else 0.8f
    }

    private fun changetextBtncolor(bool: Boolean)  {

        if (bool) {
            context?.let { ContextCompat.getColor(it, R.color.colorPrimaryDark2) }?.let {
                sendEmail.setTextColor(it)
            };

            context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }?.let {
                sendEmail.setBackgroundColor(it)
            }
            //sendEmail.setTextColor(resources.getColor(R.color.colorPrimaryDark2))
        } else {

            context?.let { ContextCompat.getColor(it, R.color.loading) }?.let {
                sendEmail.setTextColor(it)
            };
        }
    }

    private fun isEmailValid(email: String?): Boolean {

        return if (email == null) false
        else Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validEmail(e_mail: String?) {

        if (!isEmailValid(e_mail)) {
            email_rp.error = "Introduce un email válido"
            emailValid = false
        } else {
            emailValid = true
        }
    }
    private fun succeedResetPass() {
        Toast.makeText(activity, "Hemos enviado un correo a $mail. Revisa tu correo para continuar ", Toast.LENGTH_LONG).show()

    }
}