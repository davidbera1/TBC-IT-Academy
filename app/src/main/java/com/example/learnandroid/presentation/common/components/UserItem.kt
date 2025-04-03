package com.example.learnandroid.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.learnandroid.presentation.common.LoadImage
import com.example.learnandroid.presentation.model.UserUi

@Composable
fun UserItem(
    user: UserUi,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        val (avatar, fullName, email, id) = createRefs()

        LoadImage(
            url = user.avatar,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(150.dp)
                .constrainAs(avatar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = user.fullName,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(fullName) {
                top.linkTo(parent.top, margin = 10.dp)
                start.linkTo(avatar.end, margin = 10.dp)
            }
        )

        Text(
            text = user.id.toString(),
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(id) {
                bottom.linkTo(parent.bottom, margin = 10.dp)
                start.linkTo(avatar.end, margin = 10.dp)
            }
        )

        Text(
            text = user.email,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(email) {
                bottom.linkTo(fullName.top)
                top.linkTo(id.bottom)
                start.linkTo(avatar.end, margin = 10.dp)
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun UserItemPreview() {
    UserItem(
        user = UserUi(
            id = 1,
            fullName = "Jemal Kakauridze",
            email = "jemala@gmail.com",
            avatar = ""
        )
    )
}