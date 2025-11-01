package com.example.uthsmarttasks

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
object Routes {
    const val LOGIN = "login"
    const val PROFILE = "profile"
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // UTHSmartTasksTheme { // Bọc ngoài bằng Theme của bạn
            AppNavigation()
            // }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()


    val auth = FirebaseAuth.getInstance()

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current


    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)) // Rất quan trọng
            .requestEmail()
            .build()
    }

    // 2. Tạo Google Sign-In Client
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    // 3. Tạo Activity Result Launcher
    val authResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)


            coroutineScope.launch {
                try {
                    auth.signInWithCredential(credential).await()

                    // Yêu cầu: Thông báo kết quả
                    showToast(context, "Đăng nhập thành công!")

                    // Yêu cầu: Điều hướng vào màn hình chi tiết
                    navController.navigate(Routes.PROFILE) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                } catch (e: Exception) {
                    showToast(context, "Đăng nhập Firebase thất bại: ${e.message}")
                }
            }
        } catch (e: ApiException) {
            showToast(context, "Lỗi Google Sign-In: ${e.statusCode}")
        }
    }

    // Kiểm tra xem đã đăng nhập chưa
    val startDestination = if (auth.currentUser != null) Routes.PROFILE else Routes.LOGIN

    NavHost(navController = navController, startDestination = startDestination) {

        // Màn hình Login
        composable(Routes.LOGIN) {
            LoginScreen(
                onGoogleSignInClicked = {
                    authResultLauncher.launch(googleSignInClient.signInIntent)
                }
            )
        }

        // Màn hình Profile
        composable(Routes.PROFILE) {
            ProfileScreen(
                onBackClicked = {
                    auth.signOut()
                    googleSignInClient.signOut()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.PROFILE) { inclusive = true }
                    }
                }
            )
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
