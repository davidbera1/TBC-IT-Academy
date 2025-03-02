package com.example.learnandroid.utils

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView

fun TextView.setHtmlText(htmlString: String?) {
    if (htmlString == null) {
        this.visibility = View.GONE
    } else {
        text = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
        movementMethod = LinkMovementMethod.getInstance()
    }
}

