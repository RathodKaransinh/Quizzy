package com.example.quizzy.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzy.databinding.ActivityAboutBinding


class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAboutBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        // Declaring a string
        val mString = "Instagram"
        val lString = "Facebook"

        // Creating a Spannable String
        // from the above string
        val mSpannableString = SpannableString(mString)
        val lSpannableString = SpannableString(lString)

        // Setting underline style from
        // position 0 till length of
        // the spannable string
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        lSpannableString.setSpan(UnderlineSpan(), 0, lSpannableString.length, 0)

        // Displaying this spannable
        // string in TextView
        binding.instagram.text = mSpannableString
        binding.linkedIn.text = lSpannableString

        binding.instagram.setOnClickListener{
            val uri: Uri = Uri.parse("http://instagram.com/_u/rathod_karansinh")

            val i = Intent(Intent.ACTION_VIEW, uri)

            i.setPackage("com.instagram.android")

            try {
                startActivity(i)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/rathod_karansinh/")
                    )
                )
            }
        }

        binding.linkedIn.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    "http://www.facebook.com/100010133744471/"
                )
            )
            startActivity(browserIntent)
        }
    }
}