package com.example.uthsmarttasks // Thay đổi package name nếu cần

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FCM_SERVICE"


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "Nhận từ: ${remoteMessage.from}")


        remoteMessage.notification?.let {
            Log.d(TAG, "Tiêu đề thông báo: ${it.title}")
            Log.d(TAG, "Nội dung thông báo: ${it.body}")

        }
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

    }
}
