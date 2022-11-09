package com.example.quizzy.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizzy.*
import com.example.quizzy.adapters.Adapter
import com.example.quizzy.databinding.ActivityMainBinding
import com.example.quizzy.models.Question
import com.example.quizzy.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private var quizzes=ArrayList<Quiz>()
    private lateinit var firestore: FirebaseFirestore
    private var quizdates = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        setUpViews()
    }

    private fun setUpViews() {
        setUpFirestore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setUpDatePicker() {
        binding.btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATE-PICKER", datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))
                if (quizdates.contains(date)){
                    val intent = Intent(this, QuestionActivity::class.java)
                    intent.putExtra("DATE", date)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Quiz for $date is not available", Toast.LENGTH_SHORT).show()
                }
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATE-PICKER", datePicker.headerText)

            }
            datePicker.addOnCancelListener {
                Log.d("DATE-PICKER", "Date Picker Cancelled")
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter= Adapter(this, quizzes)
        binding.quizRecyclerView.layoutManager=GridLayoutManager(this, 2)
        binding.quizRecyclerView.adapter=adapter
    }

    private fun setUpDrawerLayout() {
        setSupportActionBar(binding.topAppBar)
        actionBarDrawerToggle=ActionBarDrawerToggle(this, binding.drawerlayout,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()
        binding.navigationView.menu.findItem(R.id.profile).setOnMenuItemClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            binding.drawerlayout.closeDrawers()
            true
        }

        binding.navigationView.menu.findItem(R.id.about).setOnMenuItemClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            binding.drawerlayout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpFirestore() {
        retrieveDataFromFirestore()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveDataFromFirestore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizzes")
        collectionReference
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val questionsfire=document.data["questions"] as ArrayList<Map<String, String>>
                    val questions=ArrayList<Question>()
                    for (q in questionsfire){
                        questions.add(Question(q["description"] as String, q["option1"] as String, q["option2"] as String, q["option3"] as String, q["option4"] as String, q["answer"] as String, ""))
                    }
                    quizdates.add(document.data["title"] as String)
                    quizzes.add(Quiz(document.data["title"] as String, questions))
                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("ERROR", "Error getting documents: ", exception)
            }
    }
}