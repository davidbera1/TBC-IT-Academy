package com.example.learnandroid.utils

import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView

fun TextView.setHtmlText(htmlString: String?) {
    text = Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
    movementMethod = LinkMovementMethod.getInstance()
}