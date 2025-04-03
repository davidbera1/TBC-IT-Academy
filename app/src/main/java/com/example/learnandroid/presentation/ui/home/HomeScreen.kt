package com.example.learnandroid.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.learnandroid.R
import com.example.learnandroid.presentation.common.Purple
import com.example.learnandroid.presentation.common.components.UserItem
import com.example.learnandroid.presentation.model.UserUi
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    val users = state.pagingData.collectAsLazyPagingItems()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (tvHome, tvRegisteredUsers, progressBar, lazyColumn, btnProfile) = createRefs()

        Text(
            text = stringResource(id = R.string.home),
            fontSize = 40.sp,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(tvHome) {
                    top.linkTo(parent.top, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = stringResource(id = R.string.registered_users),
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .constrainAs(tvRegisteredUsers) {
                    top.linkTo(tvHome.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        if (users.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(progressBar) {
                        top.linkTo(tvHome.bottom, margin = 10.dp)
                        start.linkTo(tvRegisteredUsers.end, margin = 20.dp)
                    }
            )
        }

        LazyColumn(
            modifier = Modifier
                .constrainAs(lazyColumn) {
                    top.linkTo(tvRegisteredUsers.bottom, margin = 10.dp)
                    bottom.linkTo(btnProfile.top, margin = 10.dp)
                    height = Dimension.fillToConstraints
                }
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            items(count = users.itemCount) { index ->
                val user = users[index]
                if (user != null) {
                    UserItem(user)
                }
            }

            when (users.loadState.append) {
                LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(30.dp)
                                .constrainAs(progressBar) {
                                    top.linkTo(tvHome.bottom, margin = 10.dp)
                                    start.linkTo(tvRegisteredUsers.end, margin = 20.dp)
                                }
                        )
                    }
                }

                is LoadState.Error -> {
                    val error = users.loadState.append as LoadState.Error
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(error.error.message ?: "Error loading more", color = Color.Red)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { users.retry() }) {
                                Text("Retry")
                            }
                        }
                    }
                }

                is LoadState.NotLoading -> Unit
            }
        }

        Button(
            onClick = { onEvent(HomeEvent.ProfileButtonClicked) },
            colors = ButtonDefaults.buttonColors(containerColor = Purple),
            modifier = Modifier
                .constrainAs(btnProfile) {
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = stringResource(id = R.string.profile),
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val testUsers = listOf(
        UserUi(
            id = 1,
            fullName = "Jemal Kakauridze",
            email = "jemala@gmail.com",
            avatar = ""
        ),
        UserUi(
            id = 2,
            fullName = "Ucha Zeragia",
            email = "Ucha@gmail.com",
            avatar = ""
        )
    )

    val pagingDataFlow = flowOf(PagingData.from(testUsers))
    val testState = HomeState(pagingData = pagingDataFlow)

    HomeScreen(
        state = testState,
        onEvent = {}
    )
}
