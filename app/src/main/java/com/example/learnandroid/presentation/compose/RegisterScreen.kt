package com.example.learnandroid.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnandroid.R
import com.example.learnandroid.presentation.compose.components.CustomTextField

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    repeatPassword: String,
    registerButtonClicked: () -> Unit,
    backButtonClicked: () -> Unit,
    updateEmailValue: (String) -> Unit,
    updatePasswordValue: (String) -> Unit,
    updateRepeatPasswordValue: (String) -> Unit,
    isLoading: Boolean = false,
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

        IconButton(
            onClick = { backButtonClicked.invoke() },
            modifier = Modifier
                .padding(start = 20.dp, top = 20.dp)
                .clip(CircleShape)
                .size(36.dp)
                .background(Color.White)
        ) {
            Icon(
                painter = painterResource(R.drawable.back),
                contentDescription = null
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 95.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
                text = stringResource(R.string.register),
                fontSize = 48.sp
            )

            Image(
                painter = painterResource(R.drawable.register_photo), contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 33.dp)

            )

            CustomTextField(
                value = email,
                onValueChange = { updateEmailValue.invoke(it) },
                hint = stringResource(R.string.email),
                startIcon = R.drawable.email,
                modifier = Modifier.padding(top = 56.dp)
            )

            CustomTextField(
                value = password,
                onValueChange = { updatePasswordValue.invoke(it) },
                textVisible = false,
                hint = stringResource(R.string.password),
                startIcon = R.drawable.password,
                endIconOn = R.drawable.eye,
                endIconOff = R.drawable.eye_off,
                modifier = Modifier.padding(top = 20.dp)
            )

            CustomTextField(
                value = repeatPassword,
                onValueChange = { updateRepeatPasswordValue.invoke(it) },
                textVisible = false,
                hint = stringResource(R.string.repeat_password),
                startIcon = R.drawable.password,
                endIconOn = R.drawable.eye,
                endIconOff = R.drawable.eye_off,
                modifier = Modifier.padding(top = 20.dp)
            )

            Button(
                onClick = { registerButtonClicked.invoke() },
                enabled = !isLoading,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 50.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple)
            ) {
                Text(
                    text = stringResource(R.string.register),
                    fontSize = 23.sp,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    color = Color.White
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
fun RegisterScreenPreview() {
    RegisterScreen(
        email = "",
        password = "",
        repeatPassword = "",
        registerButtonClicked = {},
        backButtonClicked = {},
        updateEmailValue = {},
        updatePasswordValue = {},
        updateRepeatPasswordValue = {},
        isLoading = false
    )
}