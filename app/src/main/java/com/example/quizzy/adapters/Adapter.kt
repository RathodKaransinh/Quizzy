package com.example.quizzy.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.quizzy.R
import com.example.quizzy.activities.QuestionActivity
import com.example.quizzy.models.Quiz
import com.example.quizzy.utils.ColorPicker

class Adapter(private val context: Context, private val quizzes: ArrayList<Quiz>) : RecyclerView.Adapter<Adapter.QuizViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.quiz_item, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.quiztitle.text = quizzes[position].title
        holder.cardcontainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.itemView.setOnClickListener {
            val intent = Intent(context, QuestionActivity::class.java)
            intent.putExtra("DATE", quizzes[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    inner class QuizViewHolder(itemView : View) : ViewHolder(itemView){
        val cardcontainer: CardView = itemView.findViewById(R.id.cardContainer)
        val quiztitle: TextView = itemView.findViewById(R.id.quizTitle)
    }
}