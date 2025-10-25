package com.example.tuan4.thuchanh
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Management : Screen("management")
    object Effectiveness : Screen("effectiveness")
    object Notification : Screen("notification")
    object Home : Screen("home")
}
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route // Bắt đầu ở màn hình Welcome
    ) {
        // Màn hình 1: Welcome
        composable(Screen.Welcome.route) {
            // Chúng ta chỉ cần truyền 1 lambda rỗng vì màn hình này
            // không có nút bấm. (Có thể làm nó tự chuyển sau 2s)
            OnboardingPageLayout(
                page = WelcomePage(),
                onNextClick = {},
                onGetStartedClick = {}
            )

             LaunchedEffect(key1 = true) {
               delay(2000)
               navController.navigate(Screen.Management.route)
             }
        }

        // Màn hình 2: Management
        composable(Screen.Management.route) {
            OnboardingPageLayout(
                page = ManagementPage(), // Dùng class OOP
                onNextClick = {
                    navController.navigate(Screen.Effectiveness.route) // Chuyển màn hình
                },
                onGetStartedClick = {}
            )
        }

        // Màn hình 3: Effectiveness
        composable(Screen.Effectiveness.route) {
            OnboardingPageLayout(
                page = EffectivenessPage(), // Dùng class OOP
                onNextClick = {
                    navController.navigate(Screen.Notification.route) // Chuyển màn hình
                },
                onGetStartedClick = {}
            )
        }

        composable(Screen.Notification.route) {
            OnboardingPageLayout(
                page = NotificationPage(),
                onNextClick = {},
                onGetStartedClick = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Welcome.route) { // Thay bằng route bắt đầu của bạn
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}