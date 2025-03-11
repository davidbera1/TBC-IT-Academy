package com.example.learnandroid.presentation.util

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.example.learnandroid.R
import com.example.learnandroid.presentation.model.LoginResultUiActions
import com.example.learnandroid.presentation.model.RegisterResultUiActions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UiUtils @Inject constructor() {

    fun handleErrorMessage(context: Context, errorMessage: String?) {
        if (errorMessage != null) {
            context.showToast(errorMessage)
        }
    }

    fun handleLoader(progressBar: ProgressBar, loadingView: View, loader: Boolean?) {
        if (loader == true) {
            progressBar.show()
            loadingView.show()

        } else if (loader == false) {
            progressBar.hide()
            loadingView.hide()
        }
    }

    fun handleAction(button: Button, action: Any) {
        when (action) {
            is LoginResultUiActions -> {
                if (action == LoginResultUiActions.ENABLE_LOGIN_BUTTON) {
                    button.enable()
                    button.setBackgroundResource(R.drawable.purple_button_background)
                } else if (action == LoginResultUiActions.DISABLE_LOGIN_BUTTON) {
                    button.disable()
                    button.setBackgroundResource(R.drawable.gray_button_background)
                }
            }

            is RegisterResultUiActions -> {
                if (action == RegisterResultUiActions.ENABLE_LOGIN_BUTTON) {
                    button.enable()
                    button.setBackgroundResource(R.drawable.purple_button_background)
                } else if (action == RegisterResultUiActions.DISABLE_LOGIN_BUTTON) {
                    button.disable()
                    button.setBackgroundResource(R.drawable.gray_button_background)
                }
            }
        }
    }
}