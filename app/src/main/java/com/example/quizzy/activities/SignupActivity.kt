package com.example.quizzy.activities

import android.content.ContentValues.TAG
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

class SignupActivity : AppCompatActivity() {

    private lateinit var email : EditText
    private lateinit var pass : EditText
    private lateinit var auth : FirebaseAuth
    private lateinit var signupbtn : Button
    private lateinit var loginnav : TextView
    private lateinit var confirmpass : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        email=findViewById(R.id.signupEmailAddress)
        pass=findViewById(R.id.signupPassword)
        auth=FirebaseAuth.getInstance()
        signupbtn=findViewById(R.id.signupbtn)
        loginnav=findViewById(R.id.loginnav)
        confirmpass=findViewById(R.id.signupConfirmPassword)

        signupbtn.setOnClickListener{
            register()
        }

        loginnav.setOnClickListener{
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun register(){
        val emailadd=email.text.toString()
        val password=pass.text.toString()
        val confirmpassword=confirmpass.text.toString()

        if (emailadd.isBlank() || password.isBlank() || confirmpassword.isBlank()){
            Toast.makeText(this,"Email and password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmpassword){
            Toast.makeText(this,"Password and Confirm password do not match",Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(emailadd, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Account created successfully, login using your account credentials", Toast.LENGTH_LONG).show()
                    auth.signOut()
                    val intent= Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Log.w(TAG, "createUserWithEmail:failure", it.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}