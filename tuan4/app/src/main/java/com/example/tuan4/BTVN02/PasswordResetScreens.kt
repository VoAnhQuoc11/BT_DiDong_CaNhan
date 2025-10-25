package com.example.tuan4.BTVN02


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle



@Composable
fun ForgetPasswordScreen(
    // 1. Thêm 3 tham số mới
    resultEmail: String?,
    resultCode: String?,
    resultPassword: String?,
    // ----------------
    onNextClick: (email: String) -> Unit
) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp)) // Cho Logo
        Text("Forget Password?", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Enter your Email, we will send you a verification code.", textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Your Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { onNextClick(email) }, modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Text("Next")
        }

        // --- 2. THÊM VÀO ĐÂY (Hiển thị kết quả) ---
        Spacer(modifier = Modifier.height(24.dp))

        // Nếu có kết quả trả về thì mới hiển thị
        if (resultEmail != null && resultCode != null && resultPassword != null) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Dữ liệu đã nhận về:\n")
                    }
                    append("Email: $resultEmail\n")
                    append("Code: $resultCode\n")
                    append("Password: $resultPassword")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                fontSize = 16.sp
            )
        }
        // ----------------------------------------
    }
}

@Composable
fun VerifyCodeScreen(onNextClick: (code: String) -> Unit,
                     onBackClick: () -> Unit) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.align(Alignment.Start).clickable { onBackClick() })
        Spacer(modifier = Modifier.height(80.dp)) // Cho Logo
        Text("Verify Code", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Enter the the code we just sent you on your registered Email", textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))

        Spacer(modifier = Modifier.height(32.dp))

        // Giao diện 6 ô mới
        OtpTextField(
            otpText = code,
            onOtpTextChange = {
                if (it.length <= 6) {
                    code = it
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onNextClick(code) }, // <-- Sửa ở đây
            enabled = code.length == 6, // Chỉ cho phép click khi nhập đủ 6 số
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Next")
        }
    }
}

// --- Composable MỚI cho 6 ô ---
// --- Composable cho 6 ô OTP (PHIÊN BẢN SỬA LỖI) ---
@Composable
fun OtpTextField(
    otpText: String,
    onOtpTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    // Tự động focus và hiện bàn phím khi vào màn hình
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    // 1. Box chứa cả 2 (ô ẩn và ô hiện)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { focusRequester.requestFocus() }, // Click vào đâu cũng focus
        contentAlignment = Alignment.Center
    ) {
        // 2. TextField ẩn (BÂY GIỜ ĐÃ CÓ KÍCH THƯỚC)
        BasicTextField(
            value = otpText,
            onValueChange = onOtpTextChange,
            modifier = Modifier
                .matchParentSize() // Tô đầy Box cha
                .focusRequester(focusRequester)
                .alpha(0f), // Làm cho nó vô hình
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = LocalTextStyle.current.copy(color = Color.Transparent) // Ẩn luôn con trỏ
        )

        // 3. Hàng 6 ô để hiển thị (NẰM TRÊN TEXTFIELD ẨN)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(6) { index ->
                val char = otpText.getOrNull(index)?.toString() ?: ""
                val isFocused = otpText.length == index // Ô hiện tại

                Box(
                    modifier = Modifier
                        .size(width = 50.dp, height = 60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = if (isFocused) MaterialTheme.colorScheme.primary else Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = char,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}


// --- Màn 3 ---
@Composable
fun ResetPasswordScreen(onNextClick: (password: String) -> Unit,
                        onBackClick: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.align(Alignment.Start).clickable { onBackClick() })
        Spacer(modifier = Modifier.height(80.dp)) // Cho Logo
        Text("Create new password", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Your new password must be different form previously used password", textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            // Chỉ cho phép click khi 2 password khớp và không rỗng
            enabled = password.isNotEmpty() && password == confirmPassword,
            onClick = { onNextClick(password) },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Next")
        }
    }
}

// --- Màn 4 ---
@Composable
fun ConfirmScreen(
    email: String,
    code: String,
    password: String,
    onSummitClick: () -> Unit,
    onBackClick: () -> Unit // Thêm tham số này cho nút quay lại
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nút quay lại
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.Start)
                .clickable { onBackClick() } // Gọi hành động quay lại
        )

        Spacer(modifier = Modifier.height(80.dp)) // Cho Logo

        Text("Confirm", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("We are here to help you!", textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))

        // 1. Hiển thị email (từ Màn 1)
        OutlinedTextField(
            value = email,
            onValueChange = {},
            readOnly = true, // Không cho sửa
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 2. Hiển thị code (từ Màn 2)
        OutlinedTextField(
            value = code, // Giống trong ảnh là '123456'
            onValueChange = {},
            readOnly = true, // Không cho sửa
            label = { Text("Verification Code") },
            leadingIcon = { Icon(Icons.Default.ArrowBack, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 3. Hiển thị password (từ Màn 3)
        OutlinedTextField(
            value = password,
            onValueChange = {},
            readOnly = true, // Không cho sửa
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation() // Che password đi
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Nút Summit (Gửi về Màn 1)
        Button(onClick = onSummitClick, modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Text("Summit")
        }
    }
}