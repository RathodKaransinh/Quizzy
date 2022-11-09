package com.example.quizzy.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzy.models.Quiz
import com.example.quizzy.R
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {

    private lateinit var quiz: Quiz
    private lateinit var txtAnswer : TextView
    private lateinit var txtScore : TextView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        txtAnswer=findViewById(R.id.txtAnswer)
        txtScore=findViewById(R.id.txtScore)

        setUpViews()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson(quizData, Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setAnswerView() {
        val builder = StringBuilder("")
        for (question in quiz.questions) {
            builder.append("<font color'#18206F'><b>Question : ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Correct Answer : ${question.answer}</font><br/><br/>")
            if (question.useranswer==question.answer){
                builder.append("<font color='#009688'>Your Answer : ${question.useranswer}</font><br/><br/>")
            }
            else{
                builder.append("<font color='#FF0000'>Your Answer : ${question.useranswer}</font><br/><br/>")
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT)
        } else {
            txtAnswer.text = Html.fromHtml(builder.toString(), 0)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateScore() {
        var score = 0
        for (question in quiz.questions) {
            if (question.answer == question.useranswer) {
                score += 10
            }
        }
        txtScore.text = "Your Score : $score"
    }
}