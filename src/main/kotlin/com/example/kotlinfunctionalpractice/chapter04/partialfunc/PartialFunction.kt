package com.example.kotlinfunctionalpractice.chapter04.partialfunc

import java.lang.IllegalArgumentException

/**
 * # 부분함수
 *
 * # 하스켈과 같은 함수형 언어에서는 Maybe나 Either를 반환한다.
 * # 부분함수란 모든 간으한 입력 중, 일부 입력에 대한 결과만 정의한 함수를 의미한다.
 */
class PartialFunction<P, R>(
    private val condition: (P) -> Boolean,
    private val f: (P) -> R,
) : (P) -> R {
    override fun invoke(p: P): R = when {
        condition(p) -> f(p)
        else -> throw IllegalArgumentException("$p isn't supported.")
    }

    // 4-1 연습문제
    fun invokeOrElse(p: P, default: R): R = when {
        condition(p) -> f(p)
        else -> default
    }

    // 4-1 연습문제
    fun orElse(that: PartialFunction<P, R>): PartialFunction<P, R> =
        PartialFunction({ this.isDefinedAt(it) || that.isDefinedAt(it) }
        ) {
            when {
                this.isDefinedAt(it) -> this(it)
                that.isDefinedAt(it) -> that(it)
                else -> throw IllegalArgumentException("$it isn't defined")
            }
        }

    fun isDefinedAt(p: P): Boolean = condition(p)
}

fun main(string: Array<String>) {
    val condition: (Int) -> Boolean = { it in 1..3 }
    val body: (Int) -> String = {
        when (it) {
            1 -> "One"
            2 -> "Two"
            3 -> "Three"
            else -> throw IllegalArgumentException()
        }
    }

    val oneTwoThree = PartialFunction(condition, body)

    if (oneTwoThree.isDefinedAt(4)) {
        print(oneTwoThree(4))
    } else {
        print("isDefinedAt(x) return false\n")
    }

    // p.99 4-1 연습문제
    val example1 = PartialFunction(condition, body)
    val invokeOrElseRes = example1.invokeOrElse(4,"default")
    println("invokeOrElse Res : $invokeOrElseRes")

    val example2 = PartialFunction(condition, body)
    val orElseRes = example2.orElse(example1)
    println("orElseRes Res : ${orElseRes.invokeOrElse(5, "ddd")}")
}
