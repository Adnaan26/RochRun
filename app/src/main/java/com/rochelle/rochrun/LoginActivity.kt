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

class LoginActivity : AppCompatActivity() {
    private lateinit var tvRedirectSignUp: TextView
    lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    lateinit var btnLogin: Button

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Animation for card View
        val cardView = findViewById<MaterialCardView>(R.id.cardContainer)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        cardView.startAnimation(fadeIn)

        // View Binding
        tvRedirectSignUp = findViewById(R.id.tvRedirectSignUp)
        btnLogin = findViewById(R.id.btnLogin)
        etEmail = findViewById(R.id.etEmailAddress)
        etPass = findViewById(R.id.etPassword)

        // initialising Firebase auth object
        auth = FirebaseAuth.getInstance()

        //animation for button click
        val clickAnim = AnimationUtils.loadAnimation(this, R.anim.button_click)
        btnLogin.setOnClickListener {
            it.startAnimation(clickAnim)
            login()
        }

        //redirects to MainActivity/SignUp
        tvRedirectSignUp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // using finish() to end the activity
            finish()
        }
    }

    //method created for login
    private fun login() {
        //setting variables to values in textboxes
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        // calling signInWithEmailAndPassword(email, pass)
        // function using Firebase auth object
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                //if login successful
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
            } else
            //if login fails
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }

}