package com.example.demo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        val closeButton = findViewById<Button>(R.id.closeDialogButton)

        // Đặt trình xử lý click để đóng Activity này
        closeButton.setOnClickListener {
            finish()
        }
    }
}