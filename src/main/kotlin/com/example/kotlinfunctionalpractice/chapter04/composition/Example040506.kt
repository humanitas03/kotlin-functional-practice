package com.example.kotlinfunctionalpractice.chapter04.composition

fun main(args: Array<String>) {

    // 4-5
    val list = listOf(1, 2, 3, 4, 5, 6, 7)
    val list2 = listOf(10, 2, 13, 4, 0, 6, 1)
    val max: (List<Int>) -> Int = { list: List<Int> -> list.maxOrNull()!! }
    val power: (Int) -> Int = { value: Int -> value * value }
    val maxPowerCompose = { list: List<Int> -> power(max(list)) }

    println(maxPowerCompose(list))

    // 4-6
    val composed = {list: List<Int> -> (power compose max)(list)}
    println(composed(list2))

}
