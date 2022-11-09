package com.example.quizzy.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizzy.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var loginbtn : Button
    private lateinit var auth : FirebaseAuth
    private lateinit var email : EditText
    private lateinit var pass : EditText
    private lateinit var signupnav : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginbtn=findViewById(R.id.loginbtn)
        auth= FirebaseAuth.getInstance()
        email=findViewById(R.id.loginTextEmailAddress)
        pass=findViewById(R.id.loginTextPassword)
        signupnav=findViewById(R.id.signupnav)
        loginbtn.setOnClickListener {
            auth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener(this){
                    if (it.isSuccessful){
                        Toast.makeText(this, "Logged in successfully.",
                            Toast.LENGTH_LONG).show()
                        val intent= Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", it.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        signupnav.setOnClickListener{
            val intent= Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}