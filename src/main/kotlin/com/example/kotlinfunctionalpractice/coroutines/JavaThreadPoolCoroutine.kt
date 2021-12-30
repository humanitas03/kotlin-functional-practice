package com.example.kotlinfunctionalpractice.coroutines

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

fun main() = runBlocking {
    val dispatcher = Executors.newFixedThreadPool(10)
        .asCoroutineDispatcher()

    withContext(dispatcher) {
        delay(100L)
        println(Thread.currentThread().name)
    }

    dispatcher.close() // 스레드풀 종료
}

suspend fun useFunctionSample() = Executors.newFixedThreadPool(10).asCoroutineDispatcher().use {
    withContext(it) {
        delay(100L)
        println(Thread.currentThread().name)
    }
}
