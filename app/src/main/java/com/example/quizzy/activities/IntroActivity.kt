package com.example.quizzy.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzy.R
import com.google.firebase.auth.FirebaseAuth


class IntroActivity : AppCompatActivity() {

    private lateinit var introbtn : Button
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        introbtn=findViewById(R.id.introbtn)
        auth=FirebaseAuth.getInstance()
        val currentuser=auth.currentUser

        if (currentuser != null){
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        introbtn.setOnClickListener {
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}