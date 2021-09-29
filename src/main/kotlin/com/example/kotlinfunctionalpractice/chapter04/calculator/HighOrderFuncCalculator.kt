package com.example.kotlinfunctionalpractice.chapter04.calculator

fun higherOrderFunction1(func: () -> Unit): Unit {
    func()
}

fun higherOrderFunction2(): () -> Unit {
    return { println("Hello, Kotlin")}
}

fun main(args:Array<String>){
    val sum: (Int, Int) -> Int = {x, y -> x+y}
    val minus: (Int, Int) -> Int = {x, y -> x-y}
    val product: (Int, Int) -> Int = {x, y -> x*y}

    println(higherOrder(sum, 1,5))
    println(higherOrder(minus, 5,2))
    println(higherOrder(product, 4,2))
}

fun higherOrder(func: (Int, Int) -> Int, x: Int, y: Int): Int = func(x, y)