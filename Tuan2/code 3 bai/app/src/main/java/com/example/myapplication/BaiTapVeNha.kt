package com.example.myapplication

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BaiTapVeNha : AppCompatActivity() {

    // Khai báo các thành phần UI theo ID đã sửa trong XML (battapvenha.xml)
    private lateinit var edtName: EditText
    private lateinit var edtAge: EditText
    private lateinit var btnCheck: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SỬ DỤNG FILE LAYOUT BÀI TẬP VỀ NHÀ (Giả định tên là battapvenha)
        // Nếu tên file của bạn là thuchanh02_1.xml, hãy đổi lại là R.layout.thuchanh02_1
        setContentView(R.layout.battapvenha)

        // 1. Ánh xạ (tìm) các View theo ID
        // Đảm bảo các ID này khớp với file XML cuối cùng của bạn
        edtName = findViewById(R.id.edtName)
        edtAge = findViewById(R.id.edtAge)
        btnCheck = findViewById(R.id.btnCheck) // ID của nút "Kiểm tra"
        tvResult = findViewById(R.id.tvResult) // ID của TextView hiển thị kết quả

        // 2. Thiết lập sự kiện khi nhấn nút
        btnCheck.setOnClickListener {
            checkAgeAndDisplay()
        }
    }

    private fun checkAgeAndDisplay() {
        val name = edtName.text.toString().trim()
        val ageInput = edtAge.text.toString().trim()

        if (name.isEmpty() || ageInput.isEmpty()) {
            tvResult.text = "Lỗi: Vui lòng nhập đủ Họ tên và Tuổi."
            tvResult.setTextColor(Color.RED)
            return
        }

        val age = ageInput.toIntOrNull()

        if (age == null || age < 0) {
            tvResult.text = "Lỗi: Tuổi không hợp lệ. Vui lòng nhập số nguyên dương."
            tvResult.setTextColor(Color.RED)
            return
        }

        val ageCategory = when (age) {
            // Người già (>65)
            in 66..Int.MAX_VALUE -> "Người già (>65)"
            // Người lớn (6-65)
            in 6..65 -> "Người lớn (6-65)"
            // Trẻ em (2-6)
            in 2..5 -> "Trẻ em (2-6)"
            // Em bé (<2)
            else -> "Em bé (<2)"
        }

        val resultMessage = "Xin chào, $name!\nBạn được phân loại là: $ageCategory"
        tvResult.text = resultMessage
        tvResult.setTextColor(Color.BLACK)
    }
}
