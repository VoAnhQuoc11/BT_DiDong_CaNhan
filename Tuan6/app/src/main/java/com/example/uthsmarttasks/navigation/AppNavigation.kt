package com.example.uthsmarttasks.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uthsmarttasks.R // <-- Bạn sẽ cần import R
import com.example.uthsmarttasks.Routes // <-- Import Routes từ MainActivity
import com.example.uthsmarttasks.api.RetrofitHelper
import com.example.uthsmarttasks.screens.DetailScreen
import com.example.uthsmarttasks.screens.HomeScreen
import com.example.uthsmarttasks.LoginScreen // <-- Import LoginScreen
import com.example.uthsmarttasks.showToast // <-- Import hàm showToast
import com.example.uthsmarttasks.viewmodel.DetailViewModel
import com.example.uthsmarttasks.viewmodel.DetailViewModelFactory
import com.example.uthsmarttasks.viewmodel.TaskViewModel
import com.example.uthsmarttasks.viewmodel.TaskViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // --- (Code Google Sign-In của bạn) ---
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // Lấy R.string.default_web_client_id. Bạn phải có file này trong res/values/strings.xml
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

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
                    showToast(context, "Đăng nhập thành công!")

                    // Điều hướng tới HOME
                    navController.navigate(Routes.HOME) {
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
    // --- (Hết code Google) ---


    // *** SỬA LỖI CRASH: TẠO FACTORY ***
    val apiService = RetrofitHelper.apiService
    val taskViewModelFactory = TaskViewModelFactory(apiService)
    val detailViewModelFactory = DetailViewModelFactory(apiService)

    // Kiểm tra xem đã đăng nhập chưa
    val startDestination = if (auth.currentUser != null) Routes.HOME else Routes.LOGIN

    NavHost(navController = navController, startDestination = startDestination) {

        // Màn hình Login
        composable(Routes.LOGIN) {
            LoginScreen( // <-- Bạn cần tạo file LoginScreen.kt
                onGoogleSignInClicked = {
                    authResultLauncher.launch(googleSignInClient.signInIntent)
                }
            )
        }

        // Màn hình Home (Đã sửa lỗi ViewModel)
        composable(Routes.HOME) { navBackStackEntry ->

            val taskViewModel: TaskViewModel = viewModel(factory = taskViewModelFactory)

            // Lắng nghe tín hiệu "deleted_task_id" (ID của task bị xóa)
            val deletedTaskId = navBackStackEntry.savedStateHandle
                .getLiveData<String>("deleted_task_id") // <-- Lắng nghe ID (String)
                .observeAsState()

            // Nếu có ID, gọi hàm removeTaskFromList() và reset tín hiệu
            LaunchedEffect(deletedTaskId.value) {
                val id = deletedTaskId.value
                if (id != null) {
                    taskViewModel.removeTaskFromList(id) // <-- GỌI HÀM MỚI
                    navBackStackEntry.savedStateHandle.set("deleted_task_id", null) // Reset
                }
            }

            HomeScreen(
                viewModel = taskViewModel,
                onTaskClick = { taskId ->
                    navController.navigate(Routes.getDetailRoute(taskId))
                },
                onLogoutClick = {
                    auth.signOut()
                    googleSignInClient.signOut()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }


        // === 2. SỬA KHỐI composable(Routes.DETAIL) ĐỂ GỬI TÍN HIỆU ===
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { navBackStackEntry ->

            val taskId = navBackStackEntry.arguments?.getString("taskId")
            val detailViewModel: DetailViewModel = viewModel(factory = detailViewModelFactory)

            if (taskId != null) {
                DetailScreen(
                    taskId = taskId,
                    viewModel = detailViewModel,
                    onBackClick = { navController.popBackStack() }, // Giữ nguyên

                    // Sửa onDeleteClick
                    onDeleteClick = {
                        // 1. Gửi ID của task bị xóa về cho màn hình Home
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("deleted_task_id", taskId) // <-- Gửi ID, không gửi true

                        // 2. Quay lại (pop)
                        navController.popBackStack()
                    }
                )
            }
        }

    }
}