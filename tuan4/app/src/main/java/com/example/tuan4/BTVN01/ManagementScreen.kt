package com.example.tuan4.BTVN01

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementScreen(
    viewModel: LibraryViewModel = viewModel() // Khởi tạo ViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = { LibraryBottomNav() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Áp dụng padding của Scaffold
                .padding(16.dp), // Thêm padding của riêng ta
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Tiêu đề
            Text(
                text = "Hệ thống Quản lý Thư viện",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // 2. Khu vực Sinh viên
            StudentSection(
                studentName = uiState.currentStudent.name,
                onChangeClick = {
                    viewModel.changeStudent() // Gọi hàm của ViewModel
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Khu vực Danh sách sách
            BookListSection(
                borrowedBooks = uiState.borrowedBooks
            )

            // 4. Spacer để đẩy nút Thêm xuống dưới
            Spacer(modifier = Modifier.weight(1f))

            // 5. Nút Thêm
            Button(
                onClick = {
                    viewModel.borrowBook() // Gọi hàm của ViewModel
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Thêm", fontSize = 16.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentSection(
    studentName: String,
    onChangeClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Sinh viên",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TextField để hiển thị tên
            OutlinedTextField(
                value = studentName,
                onValueChange = {}, // Không cho phép sửa
                readOnly = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Nút "Thay đổi"
            Button(onClick = onChangeClick) {
                Text("Thay đổi")
            }
        }
    }
}

@Composable
fun BookListSection(borrowedBooks: List<Book>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Danh sách sách",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Box lớn màu xám nhạt
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF0F0F0)) // Màu xám nhạt
                .padding(12.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            // --- Xử lý 2 trường hợp ---
            if (borrowedBooks.isEmpty()) {
                // TRƯỜNG HỢP 1: Sinh viên chưa mượn sách
                Text(
                    text = "Bạn chưa mượn quyển sách nào\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            } else {
                // TRƯỜNG HỢP 2: Hiển thị danh sách sách đã mượn
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    borrowedBooks.forEach { book ->
                        BookItemRow(book = book)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItemRow(book: Book) {
    // Hàng trắng bo góc chứa sách
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = true, // Luôn check vì đây là sách đã mượn
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFE53935)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = book.title, fontSize = 16.sp)
        }
    }
}

@Composable
fun LibraryBottomNav() {
    NavigationBar {
        // Tab 1: Quản lý (Active)
        NavigationBarItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.Home, contentDescription = "Quản lý") },
            label = { Text("Quản lý") }
        )
        // Tab 2: DS Sách
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.List, contentDescription = "DS Sách") },
            label = { Text("DS Sách") }
        )
        // Tab 3: Sinh viên
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Sinh viên") },
            label = { Text("Sinh viên") }
        )
    }
}