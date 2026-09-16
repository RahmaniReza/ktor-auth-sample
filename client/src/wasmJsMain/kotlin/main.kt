package com.reza

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.reza.di.initKoin
import com.reza.ui.App
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()

    val body = document.body ?: return
    ComposeViewport(viewportContainer = body) {
        App()
    }
}