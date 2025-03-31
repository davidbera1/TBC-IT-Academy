package com.example.learnandroid.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnandroid.R

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginButtonClicked: () -> Unit,
    updateEmailValue: (String) -> Unit,
    updatePasswordValue: (String) -> Unit,
    updateRememberMeValue: (Boolean) -> Unit,
    isLoading: Boolean = false,
    emailTextFromRegistration: String? = null,
    passwordTextFromRegistration: String? = null
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(emailTextFromRegistration, passwordTextFromRegistration) {
        emailTextFromRegistration?.let { email = it }
        passwordTextFromRegistration?.let { password = it }
    }

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
                text = stringResource(R.string.login),
                fontSize = 48.sp
            )

            Image(
                painter = painterResource(R.drawable.login_photo), contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 21.dp)

            )

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    updateEmailValue.invoke(it)
                },
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.email),
                        color = LightPurple,
                        modifier = Modifier
                            .padding(start = 23.dp, end = 33.dp)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.email),
                        contentDescription = null,
                        tint = Purple,
                        modifier = Modifier.padding(start = 33.dp)
                    )
                },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = LightPurple,
                    unfocusedContainerColor = LightPurple,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTextColor = Purple,
                    unfocusedTextColor = Purple,
                    cursorColor = Purple
                ),
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, top = 25.dp)
                    .height(50.dp)
                    .fillMaxWidth()
            )

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    updatePasswordValue.invoke(it)
                },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                placeholder = {
                    Text(
                        stringResource(R.string.password),
                        color = LightPurple,
                        modifier = Modifier
                            .padding(start = 28.dp, end = 43.dp)
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.password),
                        contentDescription = null,
                        tint = Purple,
                        modifier = Modifier
                            .padding(start = 38.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = if (isPasswordVisible) {
                                painterResource(R.drawable.eye_off)
                            } else {
                                painterResource(R.drawable.eye)
                            },
                            contentDescription = null,
                            tint = Purple,
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp)
                                .padding(end = 10.dp)
                        )
                    }
                },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = LightPurple,
                    unfocusedContainerColor = LightPurple,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTextColor = Purple,
                    unfocusedTextColor = Purple,
                    cursorColor = Purple
                ),
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, top = 20.dp)
                    .height(50.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .padding(start = 30.dp, top = 20.dp)
                    .align(alignment = Alignment.Start)
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = {
                        rememberMe = it
                        updateRememberMeValue.invoke(it)
                    }
                )

                Text(
                    text = stringResource(R.string.remember_me),
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                )
            }

            Button(
                onClick = { loginButtonClicked.invoke() },
                enabled = !isLoading,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 20.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple)
            ) {
                Text(
                    text = stringResource(R.string.login),
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
fun LoginScreenPreview() {
    LoginScreen(
        loginButtonClicked = {},
        updateEmailValue = {},
        updatePasswordValue = {},
        isLoading = false,
        emailTextFromRegistration = null,
        passwordTextFromRegistration = null,
        updateRememberMeValue = {}
    )
}