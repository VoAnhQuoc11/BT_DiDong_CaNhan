package com.example.tuan3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tuan3.ui.theme.Tuan3Theme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tuan3Theme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("ui_list") { UIListScreen(navController) }
        composable("text_detail") { TextDetailScreen(navController) }
        composable("images_detail") { ImageScreen(navController) }
        composable("field_detail") { TextFieldScreen(navController) }
        composable("row_detail") { RowLayoutScreen(navController) }
    }
}
@Composable
fun WelcomeScreen(navController: NavController) {
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Võ Anh Quốc",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Giá Rai, Bạc Liêu",
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = { navController.navigate("ui_list") },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("I'm ready")
                }
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIListScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Áp dụng Text(textAlign = Center) VÀ fillMaxWidth() để căn giữa
                    Text(
                        text = "UI Components List",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            color = Color.Blue
                        )

                    )
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Display",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            ComponentItem("Text", "Displays text") {
                navController.navigate("text_detail")
            }
            ComponentItem("Image", "Displays an image")
            {
                navController.navigate("images_detail")
            }
            Text(
                text = "Input",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            ComponentItem("TextField", "Input field for text")
            {
                navController.navigate("field_detail")
            }
            ComponentItem("PasswordField", "Input field for passwords")
            Text(
                text = "Layout",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            ComponentItem("Column", "Arranges elements vertically")
            ComponentItem("Row", "Arranges elements horizontally")
            {
                navController.navigate("row_detail")
            }
        }
    }
}

@Composable
fun ComponentItem(title: String, description: String, onClick: (() -> Unit)? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick?.invoke() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = description, fontSize = 13.sp)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDetailScreen(navController: NavController) {
    val customBrown = Color(0xFFC47A2B) // Màu gần với hình ảnh mẫu

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Text detail",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            color = Color.Blue
                        )

                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("ui_list")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_ios_24),
                            contentDescription = "Trở về",
                            tint = Color.Blue

                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 230.dp) // Tăng padding để đẩy văn bản xuống
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = buildAnnotatedString {
                    // Dòng 1: The quick
                    withStyle(style = SpanStyle(fontSize = 37.sp,
                        )) {
                        append("The ")
                    }
                    withStyle(style = SpanStyle(fontSize = 37.sp,
                        textDecoration = TextDecoration.LineThrough
                    )) {
                        append("quick ")
                    }

                    // Dòng 1: Brown (Màu và In đậm)
                    withStyle(style = SpanStyle(
                        fontSize = 37.sp,
                        fontWeight = FontWeight.Bold,
                        color = customBrown // Sử dụng màu tùy chỉnh
                    )) {
                        append("Brown")
                    }

                    // Dòng 2 (xuống dòng): fox j u m p s
                    withStyle(style = SpanStyle(fontSize = 32.sp)) {
                        append("\nfox j u m p s ")
                    }

                    // Dòng 2: over (In đậm)
                    withStyle(style = SpanStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )) {
                        append("over")
                    }

                    // Dòng 3 (xuống dòng): the
                    withStyle(style = SpanStyle(
                        fontSize = 32.sp,
                        textDecoration = TextDecoration.Underline // Gạch chân
                    )) {
                        append("\nthe ")
                    }

                    // Dòng 3: lazy
                    withStyle(style = SpanStyle(
                        fontSize = 32.sp,
                        fontStyle = FontStyle.Italic
                    )) {
                        append("lazy ")
                    }

                    // Dòng 3: dog.
                    withStyle(style = SpanStyle(fontSize = 32.sp)) {
                        append("dog.")
                    }

                },
                lineHeight = 45.sp
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageScreen(navController: NavController) {
    val imageUrl = "https://xdcs.cdnchinhphu.vn/446259493575335936/2024/8/17/gt1-1720610920454798297957-17239112310021499437011.jpg"
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Images",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            color = Color.Blue
                        )

                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("ui_list")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_ios_24),
                            contentDescription = "Trở về",
                            tint = Color.Blue
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = imageUrl,
                contentDescription = "Ảnh tải từ mạng",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Kích thước cố định cho ảnh
                    .padding(vertical = 8.dp),
                contentScale = ContentScale.Crop // Cắt ảnh để lấp đầy khung
            )

            // Text hiển thị URL
            Text(
                text = imageUrl,
                modifier = Modifier.padding(bottom = 24.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary // Màu xanh để làm nổi bật
            )

            Image(
                painter = painterResource(id = R.drawable.img_1),
                contentDescription = "Ảnh cục bộ trong ứng dụng",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp),
                contentScale = ContentScale.Crop
            )

            // Text hiển thị "In app"
            Text(
                text = "In app",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
@Composable
fun AsyncImage(
    contentDescription: String,
    modifier: Modifier,
    contentScale: ContentScale,
    model: String
) {
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldScreen(navController: NavController) {
    var inputText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TextField",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(color = Color.Blue)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("ui_list")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_ios_24),
                            contentDescription = "Trở về",
                            tint = Color.Blue
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 40.dp, vertical = 270.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 2. Composable TextField
            OutlinedTextField( // Sử dụng OutlinedTextField để có viền
                value = inputText,
                // Khi giá trị thay đổi, cập nhật biến state 'inputText'
                onValueChange = { inputText = it },
                label = { Text("Thông tin nhập") }, // Placeholder/Label
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp) // Bo tròn góc như trong hình
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 3. Text hiển thị nội dung nhập vào và tự động cập nhật
            Text(
                text = "Tự động cập nhật dữ liệu theo textfield: $inputText",
                color = Color.Red, // Màu đỏ như trong hình
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowLayoutScreen(navController: NavController) {
    // Sửa lỗi logic: Dùng màu xanh dương cho tiêu đề TopBar
    val headerColor = Color.Blue

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Row Layout",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(color = headerColor)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("ui_list")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_ios_24),
                            contentDescription = "Trở về",
                            tint = Color.Blue
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            repeat(4) {
                RowLayoutRow(
                    // Khối thứ 2 (index 1) luôn được tô màu xanh đậm
                    highlightedIndex = 1
                )

                // Khoảng cách giữa các hàng
                if (it < 3) { // Không thêm spacer sau hàng cuối cùng
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

/**
 * Hàm Composable tạo một Row với 3 khối màu có trọng lượng bằng nhau (1f, 1f, 1f)
 * @param highlightedIndex: Index của khối được bôi đậm (blue), mặc định là -1 (không bôi)
 */
@Composable
fun RowLayoutRow(
    highlightedIndex: Int = -1
) {
    val darkBlue = Color(0xFF1E88E5)
    val lightBlue = Color(0xFFBBDEFB)
    val Gray = Color(0xFF9BA0A8)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Lặp 3 lần cho 3 khối trên mỗi hàng
        repeat(3) { index ->
            val isHighlighted = index == highlightedIndex
            val color = if (isHighlighted) darkBlue else lightBlue

            Spacer(
                modifier = Modifier
                    .weight(1f) // Chia không gian đồng đều
                    .height(60.dp) // Chiều cao cố định
                    .clip(RoundedCornerShape(8.dp)) // Bo tròn góc
                    .background(color) // Màu nền
            )
        }
    }
}