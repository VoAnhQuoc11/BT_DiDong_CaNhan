package com.example.tuan4.BTVN01
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
data class LibraryUiState(

    val currentStudent: Student,

    val borrowedBooks: List<Book> = emptyList()
)
class LibraryViewModel : ViewModel() {

    private val _allBooks = listOf(
        Book(id = "s1", title = "Sách 01"),
        Book(id = "s2", title = "Sách 02"),
        Book(id = "s3", title = "Sách 03"),
        Book(id = "s4", title = "Sách 04")
    )
    private val _allStudents = listOf(
        Student(id = "sv1", name = "Nguyen Van A"),
        Student(id = "sv2", name = "Nguyen Thi B"),
        Student(id = "sv3", name = "Nguyen Van C")
    )


    private var currentStudentIndex = 0

    // --- State cho UI ---
    // Khởi tạo trạng thái ban đầu
    private val _uiState: MutableStateFlow<LibraryUiState>

    val uiState: StateFlow<LibraryUiState>
        get() = _uiState.asStateFlow()

    init {

        _allStudents[0].borrowBook(_allBooks[0])
        _allStudents[0].borrowBook(_allBooks[1])

        _allStudents[1].borrowBook(_allBooks[0])

        // SV C không mượn gì

        // Khởi tạo UI state với sinh viên đầu tiên
        _uiState = MutableStateFlow(
            LibraryUiState(
                currentStudent = _allStudents[0],
                borrowedBooks = _allStudents[0].borrowedBooks
            )
        )
    }


    fun changeStudent() {
        currentStudentIndex = (currentStudentIndex + 1) % _allStudents.size
        val newStudent = _allStudents[currentStudentIndex]

        _uiState.update {
            it.copy(
                currentStudent = newStudent,
                borrowedBooks = newStudent.borrowedBooks
            )
        }
    }

    /**
     * Xử lý sự kiện khi nhấn nút "Thêm"
     * Logic: Cho sinh viên hiện tại mượn một cuốn sách ngẫu nhiên
     * mà họ chưa mượn.
     */
    fun borrowBook() {
        val student = _uiState.value.currentStudent


        val bookToBorrow = _allBooks.firstOrNull { book ->
            !student.borrowedBooks.contains(book)
        }

        if (bookToBorrow != null) {
            student.borrowBook(bookToBorrow)

            _uiState.update {
                it.copy(
                    // Lấy danh sách sách mượn MỚI NHẤT
                    borrowedBooks = student.borrowedBooks.toList()
                )
            }
        }
    }
}