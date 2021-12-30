package com.example.kotlinfunctionalpractice.coroutines

import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports
import org.springframework.web.servlet.function.ServerResponse.async

// ktlint-disable no-wildcard-imports

suspend fun retrieve1(url: String) = coroutineScope {
    async(Dispatchers.IO) { // 코루틴을 어떤 스레드 풀에서 사용할 건지
        println("Retrieving data on ${Thread.currentThread().name}")
        delay(100L)
        "asyncResults"
    }.await()
}

suspend fun retrieve2(url: String) =
    withContext(Dispatchers.IO) {
        println("Retrieving data on ${Thread.currentThread().name}")
        delay(100L)
        "withContextResults"
    }

fun main() = runBlocking {
    val result1 = retrieve1("www.mysite.com")
    val result2 = retrieve2("www.mysite.com")
    println("Retrieving data on ${Thread.currentThread().name} $result1")
    println("Retrieving data on ${Thread.currentThread().name} $result2")
}
