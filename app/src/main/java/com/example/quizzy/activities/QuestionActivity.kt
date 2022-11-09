package com.example.quizzy.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzy.adapters.OptionAdapter
import com.example.quizzy.databinding.ActivityQuestionBinding
import com.example.quizzy.models.Question
import com.example.quizzy.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson


class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding
    private lateinit var firestore: FirebaseFirestore
    private var quizzes = ArrayList<Quiz>()
    private var questions = ArrayList<Question>()
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuestionBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        setUpFirestore()
        binding.btnPrevious.setOnClickListener {
            index--
            bindViews()
        }

        binding.btnNext.setOnClickListener {
            index++
            bindViews()
        }

        binding.btnSubmit.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            val json  = Gson().toJson(quizzes[0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
            finish()
        }
    }

    private fun bindViews() {
        binding.btnPrevious.visibility = View.GONE
        binding.btnSubmit.visibility = View.GONE
        binding.btnNext.visibility = View.GONE

        if (questions.size==1){
            binding.btnSubmit.visibility = View.VISIBLE
        }
        else{
            when(index){
                0 -> binding.btnNext.visibility = View.VISIBLE
                questions.size-1 -> {
                    binding.btnSubmit.visibility = View.VISIBLE
                    binding.btnPrevious.visibility = View.VISIBLE
                }
                else -> {
                    binding.btnPrevious.visibility = View.VISIBLE
                    binding.btnNext.visibility = View.VISIBLE
                }
            }
        }

        val question = questions[index]
        question.let {
            binding.description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            binding.optionList.layoutManager = LinearLayoutManager(this)
            binding.optionList.adapter = optionAdapter
            binding.optionList.setHasFixedSize(true)
        }
    }

    private fun setUpFirestore() {
        firestore = FirebaseFirestore.getInstance()
        val date=intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    for (document in it) {
                        val questionsfire=document.data["questions"] as ArrayList<Map<String, String>>
                        val question=ArrayList<Question>()
                        for (q in questionsfire){
                            question.add(Question(q["description"] as String, q["option1"] as String, q["option2"] as String, q["option3"] as String, q["option4"] as String, q["answer"] as String, ""))
                        }
                        quizzes.add(Quiz(document.data["title"] as String, question))
                    }
                    questions = quizzes[0].questions
                    bindViews()
                    }
                }

    }
}