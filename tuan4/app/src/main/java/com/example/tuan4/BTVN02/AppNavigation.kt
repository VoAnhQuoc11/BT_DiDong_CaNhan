package com.example.tuan4.BTVN02

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.ForgetPassword.route
    ) {

        composable(
            route = Screen.ForgetPassword.route,
            arguments = Screen.ForgetPassword.arguments // Thêm argument
        ) { backStackEntry ->
            // 1. Lấy 3 giá trị kết quả (có thể là null)
            val resultEmail = backStackEntry.arguments?.getString("email")
            val resultCode = backStackEntry.arguments?.getString("code")
            val resultPassword = backStackEntry.arguments?.getString("password")

            ForgetPasswordScreen(
                // 2. Truyền 3 giá trị này vào UI
                resultEmail = resultEmail,
                resultCode = resultCode,
                resultPassword = resultPassword,

                onNextClick = { email ->
                    navController.navigate(Screen.VerifyCode.createRoute(email))
                }
            )
        }
        composable(
            route = Screen.VerifyCode.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""

            VerifyCodeScreen(
                onNextClick = { code ->
                    navController.navigate(Screen.ResetPassword.createRoute(email, code))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // --- Màn 3: ResetPassword (Sửa logic nhận và truyền) ---
        composable(
            route = Screen.ResetPassword.route,
            // Nhận cả email và code
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("code") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val code = backStackEntry.arguments?.getString("code") ?: ""

            ResetPasswordScreen(
                onNextClick = { newPassword ->
                    navController.navigate(Screen.Confirm.createRoute(email, code, newPassword))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // --- Màn 4: Confirm (Sửa logic nhận) ---
        composable(
            route = Screen.Confirm.route,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("code") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val code = backStackEntry.arguments?.getString("code") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""

            ConfirmScreen(
                email = email,
                code = code,
                password = password,
                onSummitClick = {
                    // --- SỬA LOGIC Ở ĐÂY ---
                    // 1. Điều hướng đến Màn 1 VỚI DỮ LIỆU
                    navController.navigate(
                        Screen.ForgetPassword.createRoute(email, code, password)
                    ) {
                        // 2. Xóa TOÀN BỘ stack cũ
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                    // ------------------------
                },
                onBackClick =  {
                    navController.popBackStack()
                }
            )
        }
    }
}