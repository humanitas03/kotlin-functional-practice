package com.example.kotlinfunctionalpractice.chapter04.composition

// p.106
fun main(args: Array<String>) {
    val addThree = { i:Int -> i+3}
    val twice = { i: Int -> i*2}
    val composedFunc = addThree compose twice
    println(composedFunc(3))
}

infix fun <F,G,R> ((F)->R).compose(g: (G) -> F): (G) ->R{
    return { gInput: G -> this(g(gInput))}
}