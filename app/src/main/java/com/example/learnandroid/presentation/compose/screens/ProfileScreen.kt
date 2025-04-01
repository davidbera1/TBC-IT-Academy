package com.example.learnandroid.presentation.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.learnandroid.R
import com.example.learnandroid.presentation.compose.Purple

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    logoutButtonClicked: () -> Unit,
    email: String,
    isLoading: Boolean = false
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val (tvProfile, btnLogout, tvEmail) = createRefs()

        Text(
            text = stringResource(R.string.profile),
            fontSize = 40.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(tvProfile) {
                top.linkTo(parent.top, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "Email: $email",
            fontSize = 25.sp,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(tvEmail) {
                    top.linkTo(tvProfile.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Button(
            onClick = { logoutButtonClicked.invoke() },
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Purple),
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .height(50.dp)
                .constrainAs(btnLogout) {
                    bottom.linkTo(parent.bottom, margin = 50.dp)
                }
        ) {
            Text(
                text = stringResource(R.string.logout),
                fontSize = 23.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                color = Color.White
            )
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
fun ProfileScreenPreview() {
    ProfileScreen(
        logoutButtonClicked = {},
        email = "eve.holt@reqres.in"
    )
}