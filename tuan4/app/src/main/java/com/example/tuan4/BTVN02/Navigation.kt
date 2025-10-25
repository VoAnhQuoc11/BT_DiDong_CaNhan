package com.example.tuan4.BTVN02

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object ForgetPassword : Screen(
        // 1. Thêm các argument tùy chọn (dùng ? và &)
        "forget_password?email={email}&code={code}&password={password}"
    ) {
        // 2. Định nghĩa các argument, cho phép null (nullable)
        val arguments = listOf(
            navArgument("email") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument("code") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument("password") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )

        // 3. Hàm trợ giúp để tạo route KHI CÓ KẾT QUẢ
        fun createRoute(email: String, code: String, password: String): String {
            return "forget_password?email=$email&code=$code&password=$password"
        }

        // 4. Route gốc khi không có kết quả
        val routeWithoutArgs = "forget_password"
    }

    object VerifyCode : Screen("verify_code/{email}") {
        fun createRoute(email: String) = "verify_code/$email"
    }

    object ResetPassword : Screen("reset_password/{email}/{code}") {
        fun createRoute(email: String, code: String) = "reset_password/$email/$code"
    }

    object Confirm : Screen("confirm/{email}/{code}/{password}") {
        fun createRoute(email: String, code: String, password: String) = "confirm/$email/$code/$password"
    }
}