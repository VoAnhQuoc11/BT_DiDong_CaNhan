package com.example.tuan4.thuchanh

import androidx.annotation.DrawableRes
import com.example.tuan4.R


enum class ButtonType {
        NONE,
        NEXT,
        GET_STARTED
    }


    interface OnboardingPage {
        @get:DrawableRes
        val imageRes: Int
        val title: String
        val description: String
        val buttonType: ButtonType
    }
    class WelcomePage : OnboardingPage {
        override val imageRes: Int = R.drawable.uth_logo
        override val title: String = "UTH SmartTasks"
        override val description: String = "Welcome to the future of task management."
        override val buttonType: ButtonType = ButtonType.NONE
    }

    class ManagementPage : OnboardingPage {
        override val imageRes: Int = R.drawable.onboarding_1
        override val title: String = "Easy Time Management"
        override val description: String = "With management based on priorities, you will get the best performance."
        override val buttonType: ButtonType = ButtonType.NEXT
    }

    class EffectivenessPage : OnboardingPage {
        override val imageRes: Int = R.drawable.onboarding_2
        override val title: String = "Increase Your Effectiveness"
        override val description: String = "Plan ahead and manage your personal/team tasks all in one place."
        override val buttonType: ButtonType = ButtonType.NEXT
    }

    class NotificationPage : OnboardingPage {
        override val imageRes: Int = R.drawable.onboarding_3
        override val title: String = "Get Realtime Notification"
        override val description: String = "Get notified of the application status update and task reminders."
        override val buttonType: ButtonType = ButtonType.GET_STARTED
    }