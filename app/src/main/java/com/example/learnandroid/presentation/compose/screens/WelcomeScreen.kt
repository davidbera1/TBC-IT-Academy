package com.example.learnandroid.presentation.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnandroid.R
import com.example.learnandroid.presentation.compose.LightPurple
import com.example.learnandroid.presentation.compose.Purple

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    registerButtonClicked: () -> Unit,
    loginButtonClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(painter = painterResource(R.drawable.ellipse_top), contentDescription = null)

        Image(
            painter = painterResource(R.drawable.ellipse_bottom), contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 95.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
                text = stringResource(R.string.welcome),
                fontSize = 48.sp
            )

            Image(
                painter = painterResource(R.drawable.welcome_photo), contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 63.dp)

            )

            Button(
                onClick = { registerButtonClicked.invoke() },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 30.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple)
            ) {
                Text(
                    text = stringResource(R.string.register),
                    fontSize = 23.sp,
                    fontFamily = FontFamily(Font(R.font.roboto))
                )
            }

            Button(
                onClick = { loginButtonClicked.invoke() },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 20.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LightPurple)
            ) {
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 23.sp,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    color = Purple
                )
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = true, onClick = {})
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomePreview() {
    WelcomeScreen(registerButtonClicked = {}, loginButtonClicked = {}, isLoading = false)
}