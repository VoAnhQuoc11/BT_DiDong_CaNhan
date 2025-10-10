package com.example.myapplication

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btnCreate)
        val editText = findViewById<EditText>(R.id.edtNumber)
        val errorText = findViewById<TextView>(R.id.tvError)
        val container = findViewById<LinearLayout>(R.id.layoutContainer)

        button.setOnClickListener {
            val input = editText.text.toString().trim()
            val number = input.toIntOrNull()

            if (number == null || number <= 0) {
                errorText.visibility = View.VISIBLE
                container.visibility = View.GONE
                return@setOnClickListener
            }

            errorText.visibility = View.GONE
            container.visibility = View.VISIBLE

            container.removeAllViews()

            for (i in 1..number) {
                val cardView = CardView(this).apply {
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        150
                    )

                    params.setMargins(0, 20, 0, 0)
                    layoutParams = params
                    radius = 50f
                    setCardBackgroundColor(Color.parseColor("#FD0101"))
                }

                val textView = TextView(this).apply {
                    text = "$i"
                    textSize = 20f
                    setTextColor(Color.parseColor("#FFFFFF"))
                    gravity = android.view.Gravity.CENTER
                }

                cardView.addView(textView)

                container.addView(cardView)
            }
        }
    }

}
