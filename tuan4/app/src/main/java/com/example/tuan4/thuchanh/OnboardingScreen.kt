package com.example.tuan4.thuchanh
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
    fun OnboardingPageLayout(
    page: OnboardingPage, // Tham số kiểu trừu tượng
    onNextClick: () -> Unit,
    onGetStartedClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Phần 1: Nội dung (Ảnh, Tiêu đề, Mô tả)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Spacer(modifier = Modifier.height(60.dp))
5
                // 1. Ảnh
                Image(
                    painter = painterResource(id = page.imageRes),
                    contentDescription = page.title,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.height(40.dp))

                // 2. Tiêu đề
                Text(
                    text = page.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                // 3. Mô tả
                Text(
                    text = page.description,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),


                contentAlignment = Alignment.Center
            ) {
                when (page.buttonType) {
                    ButtonType.NEXT -> {
                        Button(
                            onClick = onNextClick,
                            modifier = Modifier.fillMaxWidth(0.8f)
                                .height(60.dp)
                        ) {
                            Text("Next")
                        }
                    }
                    ButtonType.GET_STARTED -> {
                        Button(
                            onClick = onGetStartedClick,
                            modifier = Modifier.fillMaxWidth(0.8f)
                                .height(60.dp)

                        ) {
                            Text("Get Started")
                        }
                    }
                    ButtonType.NONE -> {
                    }
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }