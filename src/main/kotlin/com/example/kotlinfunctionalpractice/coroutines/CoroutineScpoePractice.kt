package com.example.kotlinfunctionalpractice.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() = coroutineScope {
    for (i in 0 until 10) {
        launch {
            delay(1000L - i * 10)
            println("#${Thread.currentThread().name} - $i  ")
        }
    }
}
