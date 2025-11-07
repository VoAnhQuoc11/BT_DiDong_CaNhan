package com.example.uthsmarttasks

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClicked: () -> Unit
) {
    val currentUser = remember { FirebaseAuth.getInstance().currentUser }
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("PERMISSIONS", "Quyền gửi thông báo ĐÃ ĐƯỢC CẤP.")
        } else {
            Log.d("PERMISSIONS", "Quyền gửi thông báo BỊ TỪ CHỐI.")
        }
    }


    LaunchedEffect(key1 = true) {
        // A. Xin quyền (Chỉ cho Android 13 (API 33) trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Log.d("PERMISSIONS", "Quyền đã được cấp từ trước.")
                }
                else -> {
                    Log.d("PERMISSIONS", "Đang hỏi quyền gửi thông báo...")
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }


        try {
            val token = FirebaseMessaging.getInstance().token.await()
            Log.d("FCM_TOKEN", "Token của thiết bị này là: $token")
        } catch (e: Exception) {
            Log.e("FCM_TOKEN", "Lấy token thất bại", e)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Profile",modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,fontWeight = FontWeight.Bold,color = Color(
                0xFF18A4F3)) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = currentUser?.photoUrl, // Tải ảnh từ Google
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Thông tin (giống trong ảnh)
            InfoRow(label = "Name", value = currentUser?.displayName ?: "Melissa Peters")
            InfoRow(label = "Email", value = currentUser?.email ?: "melpeter@gmail.com")
            InfoRow(label = "Date of Birth", value = "23/05/1985") // Lấy cứng vì Firebase Auth không trả về ngày sinh

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = onBackClicked, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3F51B5) // Xanh nhạt
            )) {
                Text(text = "Back")

            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelSmall)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
        Divider()
    }
}