package com.example.learnandroid.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnandroid.R
import com.example.learnandroid.presentation.common.LightPurple
import com.example.learnandroid.presentation.common.Purple

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    startIcon: Int,
    textVisible: Boolean = true,
    endIconOn: Int? = null,
    endIconOff: Int? = null,
) {
    var isTextVisible by remember { mutableStateOf(textVisible) }

    Box(
        modifier = modifier
            .padding(start = 30.dp, end = 30.dp)
            .background(LightPurple, shape = RoundedCornerShape(30.dp))
            .height(50.dp)
    ) {
        Row(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .fillMaxHeight()
                .padding(start = 25.dp, end = 43.dp)
        ) {
            Icon(
                painter = painterResource(id = startIcon),
                contentDescription = null,
                tint = Purple,
                modifier = Modifier
                    .padding(end = 15.dp)
                    .align(Alignment.CenterVertically)
                    .size(30.dp)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        color = LightPurple,
                        fontSize = 18.sp
                    )
                }

                BasicTextField(
                    value = value,
                    visualTransformation =
                    if (isTextVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        color = Purple,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                )
            }
        }

        if (endIconOn != null && endIconOff != null) {
            IconButton(
                onClick = { isTextVisible = !isTextVisible },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = if (isTextVisible) {
                        painterResource(endIconOff)
                    } else {
                        painterResource(endIconOn)
                    },
                    contentDescription = null,
                    tint = Purple,
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .padding(end = 10.dp)
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun CustomTextFieldPreview() {
    CustomTextField(
        value = "eve.holt",
        onValueChange = {},
        hint = "hint",
        startIcon = R.drawable.email,
        endIconOn = R.drawable.eye,
        endIconOff = R.drawable.eye_off
    )
}