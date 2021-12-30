package com.example.kotlinfunctionalpractice.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch {
        repeat(100) {
            println("job : I'm waiting $it")
            delay(100L)
        }
    }
    delay(500L)
    println("main : That's enough waiting")
    job.cancel()
    job.join()
    println("main: Done")
}
