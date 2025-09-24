package com.rochelle.rochrun

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etConfPass: EditText
    private lateinit var etPass: EditText
    private lateinit var btnSignUp: Button
    lateinit var tvRedirectLogin: TextView

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Animation for cardview
        val cardView = findViewById<MaterialCardView>(R.id.cardContainer)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        cardView.startAnimation(fadeIn)

        // View Bindings
        etEmail = findViewById(R.id.etSEmailAddress)
        etConfPass = findViewById(R.id.etSConfPassword)
        etPass = findViewById(R.id.etSPassword)
        btnSignUp = findViewById(R.id.btnSigned)
        tvRedirectLogin = findViewById(R.id.tvRedirectLogin)

        // Initialising auth object
        auth = FirebaseAuth.getInstance()

        //animation for button click
        val clickAnim = AnimationUtils.loadAnimation(this, R.anim.button_click)
        btnSignUp.setOnClickListener {
            it.startAnimation(clickAnim)
            signUpUser()
        }

        // switching from signUp to Login
        tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    //method to signup/register
    private fun signUpUser() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val confirmPassword = etConfPass.text.toString()

        // checks if any fields are blank
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            //display toast if any of the fields are blank - all fields must be filled in
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        //checks if password and confirm password match
        if (pass != confirmPassword) {
            //displays toast if they don't match
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }

        /*
        If all of the above passes, we use the auth object and pass through the email and password
        This is saving our user in Firebase - you can check under the Authentication tab, under users
        if your data was saved
        NOTE: if you don't add authentication on the Firebase console for the app, you will have a failed
        signup even though your app is connected
         */
        val intent = Intent(this, LoginActivity::class.java)
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            } else {
                Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}