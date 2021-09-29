package com.example.kotlinfunctionalpractice.chapter05

fun imperativeWay(intList: List<Int>): Int {
    for (value in intList) {
        val doubleValue = value * value
        if (doubleValue < 10) {
            return doubleValue
        }
    }

    throw NoSuchElementException("Thereis no value")
}

fun functionalWay(intList: List<Int>): Int =
    intList
        .map { n -> n * n }
        .filter { n -> n <10 }
        .first()


fun main() {
    val bigIntList = (1..10000000).toList()
    var start = System.currentTimeMillis()
    imperativeWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms")

    start = System.currentTimeMillis()
    functionalWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms")
}