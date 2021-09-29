package com.example.kotlinfunctionalpractice.chapter04.currying

fun main(args: Array<String>) {
    val maxValue = max(10)(20)
    val maxValue2 = max(30)(11)

    println("maxValue :  $maxValue")
    println("maxValue :  $maxValue2")
}

fun max(a: Int) = { b: Int -> if (a> b) a else b }
