package com.example.tuan4.BTVN01

import com.example.tuan4.BTVN01.Book

class Student(
    val id: String,
    val name: String
) {
    // --- TÍNH ĐÓNG GÓI (ENCAPSULATION) ---
    // Danh sách sách đã mượn là 'private'.
    // Giao diện (UI) hoặc các lớp khác không thể tự ý thêm/xóa sách khỏi danh sách này.
    private val _borrowedBooks = mutableListOf<Book>()

    // Chúng ta cung cấp một danh sách 'chỉ đọc' (read-only) ra bên ngoài.
    val borrowedBooks: List<Book>
        get() = _borrowedBooks.toList() // Trả về một bản copy để đảm bảo an toàn

    /**
     * Phương thức public để sinh viên mượn sách.
     * Đây là cách duy nhất để thêm sách vào danh sách _borrowedBooks.
     */
    fun borrowBook(book: Book) {
        if (!_borrowedBooks.contains(book)) {
            _borrowedBooks.add(book)
        }
    }

    /**
     * Phương thức public để sinh viên trả sách.
     */
    fun returnBook(book: Book) {
        _borrowedBooks.remove(book)
    }
}