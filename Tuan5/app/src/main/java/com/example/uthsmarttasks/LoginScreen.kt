package com.example.uthsmarttasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun LoginScreen(
    onGoogleSignInClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.uth_logo),
            contentDescription = "UTH Logo",
            modifier = Modifier

                .fillMaxWidth(0.6f)
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "SmartTasks", fontSize = 24.sp, fontWeight = FontWeight.Bold,color = Color(
            0xFF18A4F3
        )
        )
        Text(text = "A simple and efficient to-do app",color = Color(
            0xFF18A4F3
        ))
        Spacer(modifier = Modifier.height(200.dp))

        Text(text = "Welcome",fontWeight = FontWeight.Bold,fontSize = 24.sp)
        Text(text = "Ready to explore? Log in to get started.")
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onGoogleSignInClicked,
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB3E5FC) // Xanh nhạt
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "Google Logo",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "SIGN IN WITH GOOGLE",
                modifier = Modifier.padding(start = 8.dp),
                color = Color.Black // Sets the text color to black
            )
        }
    }
}