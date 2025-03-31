package com.example.learnandroid.presentation.util

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.example.learnandroid.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UiUtils @Inject constructor() {

    fun handleLoader(
        progressBar: ProgressBar,
        loadingView: View? = null,
        button: Button? = null,
        loader: Boolean?,
        button2: Button? = null // for welcome page login button
    ) {
        if (loader == true) {
            progressBar.show()
            loadingView?.show()
            button?.disable()
            button?.setBackgroundResource(R.drawable.gray_button_background)
            button2?.disable()
            button2?.setBackgroundResource(R.drawable.gray_button_background)

        } else if (loader == false) {
            progressBar.hide()
            loadingView?.hide()
            button?.enable()
            button?.setBackgroundResource(R.drawable.purple_button_background)
            button2?.enable()
            button2?.setBackgroundResource(R.drawable.light_purple_button_background)
        }
    }
}