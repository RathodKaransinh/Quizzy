package com.example.quizzy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.quizzy.R
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var txtEmail : TextView
    private lateinit var btnLogout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        txtEmail=findViewById(R.id.txtEmail)
        btnLogout=findViewById(R.id.btnLogout)

        firebaseAuth = FirebaseAuth.getInstance()
        txtEmail.text = firebaseAuth.currentUser?.email

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }
}