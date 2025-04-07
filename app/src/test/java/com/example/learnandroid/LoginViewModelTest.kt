package com.example.learnandroid

import app.cash.turbine.test
import com.example.learnandroid.domain.common.Resource
import com.example.learnandroid.domain.model.LoginResponse
import com.example.learnandroid.domain.repository.LoginRepository
import com.example.learnandroid.domain.use_case.LoginUseCase
import com.example.learnandroid.domain.use_case.SaveUserSessionUseCase
import com.example.learnandroid.domain.use_case.ValidateEmailUseCase
import com.example.learnandroid.domain.use_case.ValidatePasswordUseCase
import com.example.learnandroid.presentation.ui.login.LoginEffect.NavigateToHome
import com.example.learnandroid.presentation.ui.login.LoginEffect.ShowSnackbar
import com.example.learnandroid.presentation.ui.login.LoginEvent.LoginButtonClicked
import com.example.learnandroid.presentation.ui.login.LoginEvent.SendUpdatedEmail
import com.example.learnandroid.presentation.ui.login.LoginEvent.SendUpdatedPassword
import com.example.learnandroid.presentation.ui.login.LoginViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    private val loginRepo: LoginRepository = mockk()
    private val validateEmailUseCase: ValidateEmailUseCase = mockk()
    private val validatePasswordUseCase: ValidatePasswordUseCase = mockk()
    private val saveSessionUseCase: SaveUserSessionUseCase = mockk(relaxed = true)
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(
            loginRepo,
            validateEmailUseCase,
            validatePasswordUseCase,
            saveSessionUseCase
        )
        viewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun `Login fails when fields are empty`() = runTest {
        viewModel.effects.test {
            viewModel.onEvent(LoginButtonClicked)
            assertEquals(ShowSnackbar("Please fill all fields"), awaitItem())
        }
    }

    @Test
    fun `Login fails when email is invalid`() = runTest {
        val email = "test"
        val password = "password123"

        every { validateEmailUseCase(email) } returns false
        every { validatePasswordUseCase(password) } returns true

        viewModel.effects.test {
            viewModel.onEvent(SendUpdatedEmail(email))
            viewModel.onEvent(SendUpdatedPassword(password))

            viewModel.onEvent(LoginButtonClicked)

            assertEquals(ShowSnackbar("Invalid email"), awaitItem())
        }
    }

    @Test
    fun `Login fails when password is invalid`() = runTest {
        val email = "eve.holt@reqres.in"
        val password = "asd"

        every { validateEmailUseCase(email) } returns true
        every { validatePasswordUseCase(password) } returns false

        viewModel.effects.test {
            viewModel.onEvent(SendUpdatedEmail(email))
            viewModel.onEvent(SendUpdatedPassword(password))

            viewModel.onEvent(LoginButtonClicked)

            assertEquals(ShowSnackbar("Invalid password"), awaitItem())
        }
    }

    @Test
    fun `Successful login triggers navigation to home`() = runTest {
        val email = "eve.holt@reqres.in"
        val password = "password123"
        val token = "token123123"
        val loginResponse = LoginResponse(token)

        every { validateEmailUseCase(email) } returns true
        every { validatePasswordUseCase(password) } returns true
        coEvery { loginRepo.login(email, password) } returns flowOf(Resource.Success(loginResponse))


        viewModel.effects.test {
            viewModel.onEvent(SendUpdatedEmail(email))
            viewModel.onEvent(SendUpdatedPassword(password))

            viewModel.onEvent(LoginButtonClicked)

            assertEquals(NavigateToHome, awaitItem())
        }
    }

    @Test
    fun `Login error from repository triggers snackbar`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val errorMessage = "Invalid credentials"

        every { validateEmailUseCase(email) } returns true
        every { validatePasswordUseCase(password) } returns true
        coEvery { loginRepo.login(email, password) } returns flowOf(Resource.Error(errorMessage))

        viewModel.effects.test {
            viewModel.onEvent(SendUpdatedEmail(email))
            viewModel.onEvent(SendUpdatedPassword(password))

            viewModel.onEvent(LoginButtonClicked)

            assertEquals(ShowSnackbar(errorMessage), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
