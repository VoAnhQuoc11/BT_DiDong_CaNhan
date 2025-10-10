package com.example.myapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.graphics.Color
class Thuchanh02 : AppCompatActivity() {

    // Ánh xạ các thành phần UI theo ID trong thuchanh02.xml
    private lateinit var edtEmail: EditText    // ID: editTextText
    private lateinit var btnCheck: Button      // ID: button
    private lateinit var tvMessage: TextView   // ID: textView4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.thuchanh02)

        edtEmail = findViewById(R.id.editTextText)
        btnCheck = findViewById(R.id.button)
        tvMessage = findViewById(R.id.textView4)

        btnCheck.setOnClickListener {
            validateEmail()
        }
    }

    private fun validateEmail() {
        val email = edtEmail.text.toString().trim()

        fun displayError(message: String) {
            tvMessage.text = message
            tvMessage.setTextColor(Color.RED)
        }

        if (email.isEmpty()) {
            displayError("Email không hợp lệ")
            return
        }

        if (!email.contains('@')) {
            displayError("Email không đúng định dạng")
            return
        }

        tvMessage.text = "Bạn đã nhập email hợp lệ"
        tvMessage.setTextColor(Color.parseColor("#4CAF50")) // Màu xanh lá
    }
}