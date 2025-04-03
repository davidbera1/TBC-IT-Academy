package com.example.learnandroid.presentation.ui.login

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.example.learnandroid.presentation.common.Purple
import com.example.learnandroid.presentation.common.components.CustomTextField

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(painter = painterResource(R.drawable.ellipse_top), contentDescription = null)

            Image(
                painter = painterResource(R.drawable.ellipse_bottom), contentDescription = null,
                modifier = Modifier.align(Alignment.BottomStart)
            )

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 95.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.login),
                    fontSize = 48.sp
                )

                Image(
                    painter = painterResource(R.drawable.login_photo),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 21.dp)
                )

                CustomTextField(
                    value = state.email,
                    onValueChange = { onEvent(LoginEvent.SendUpdatedEmail(it)) },
                    hint = stringResource(R.string.email),
                    startIcon = R.drawable.email,
                    modifier = Modifier.padding(top = 25.dp)
                )

                CustomTextField(
                    value = state.password,
                    onValueChange = { onEvent(LoginEvent.SendUpdatedPassword(it)) },
                    textVisible = false,
                    hint = stringResource(R.string.password),
                    startIcon = R.drawable.password,
                    endIconOn = R.drawable.eye,
                    endIconOff = R.drawable.eye_off,
                    modifier = Modifier.padding(top = 20.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(start = 30.dp, top = 20.dp)
                        .align(Alignment.Start)
                ) {
                    Checkbox(
                        checked = state.isRememberMeChecked,
                        onCheckedChange = { onEvent(LoginEvent.SendUpdatedRememberMe(it)) }
                    )

                    Text(
                        text = stringResource(R.string.remember_me),
                        color = Color.Black,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                Button(
                    onClick = { onEvent(LoginEvent.LoginButtonClicked) },
                    enabled = !state.isLoading,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, top = 20.dp)
                        .align(Alignment.CenterHorizontally)
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

            if (state.isLoading) {
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
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen(
        state = LoginState(),
        onEvent = {},
        snackbarHostState = SnackbarHostState()
    )
}