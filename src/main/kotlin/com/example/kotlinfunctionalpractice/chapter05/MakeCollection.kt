package com.example.kotlinfunctionalpractice.chapter05

// p.116 ~

/*sealed class로 작성된 FunList는 Nil 이거나 Cons가 된다.
*  Nil -> FunList는 아무것도 가지지 않은 Nothing 객체를 포함하고,
*  Cons -> 어떤 T 타입의 값 head와 또 다른 FunList<T>인 tail을 가진다.
*   여기서 tail이 가진 값의 타입도 T 이므로 FunList를 구성하는 모든 값의 타입은 T로 동일.
* */
sealed class FunList<out T> {
    object Nil : FunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
    // p.117
    // 함수형 컬렉션에서 제공하는 함수들은 불변성(immutability)을 지키고 부수효과를 없애기 위해
    // 원본 데이터를 변경하지 않고 가공된 데이터를 매번 새로 생성하여 반환하는 특징을 가진다.
    fun <T> FunList<T>.addHead(head: T): FunList<T> = FunList.Cons(head, this)
}

tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> FunList.Cons(value, acc).reverse() // 이함수를 동작시키려면 reverse가 있어야 한다.
    is FunList.Cons -> tail.appendTail(value, acc.addHead(head))
}

tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.reverse(acc.addHead(head))
}

fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> tail
}

fun <T> FunList<T>.getHead(): T = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> head
}

tailrec fun <T> FunList<T>.filter(acc: FunList<T> = FunList.Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> if (p(head)) {
        tail.filter(acc.addHead(head), p)
    } else {
        tail.filter(acc, p)
    }
}

tailrec fun <T, R> FunList<T>.map(acc: FunList<R> = FunList.Nil, f: (T) -> R): FunList<R> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> tail.map(acc.addHead(f(head)), f)
}

fun sum(list: FunList<Int>): Int = when (list) {
    FunList.Nil -> 0
    is FunList.Cons -> list.head + sum(list.tail)
}

// p.128 - 컬렉션의 값들을 왼쪽에서부터 오른쪽으로 줄여나가는 함수를 foldLeft라고 한다.
tailrec fun <T, R> FunList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.foldLeft(f(acc, head), f)
}

// p.131 foldRight 함수 만들기
fun <T, R> FunList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> f(head, tail.foldRight(acc, f))
}

// p.130 foldLeft 함수로 toUpper, map 함수
fun toUpper(list: FunList<Char>): FunList<Char> = list.foldLeft(FunList.Nil) {
    acc: FunList<Char>, char: Char ->
    acc.appendTail(char.uppercaseChar())
}

fun <T, R> FunList<T>.mapByFoldLeft(f: (T) -> R): FunList<R> = this.foldLeft(FunList.Nil) {
    acc: FunList<R>, x ->
    acc.appendTail(f(x))
}

// p.135 zipWith 함수
// 조합을 위한 두개 리스트 중 하나라도 비어있으면 결과는 항상 Nil이 된다.
tailrec fun <T1, T2, R> FunList<T1>.zipWith(f: (T1, T2) -> R, list: FunList<T2>, acc: FunList<R> = FunList.Nil): FunList<R> = when {
    this === FunList.Nil || list === FunList.Nil -> acc.reverse()
    else -> getTail().zipWith(f, list.getTail(), acc.addHead(f(getHead(), list.getHead())))
}

fun main(args: Array<String>) {

    /* * 연습문제 5-1, 5-2 p.117*/
    val list: FunList<Int> = FunList.Cons(
        1,
        FunList.Cons(
            2,
            FunList.Cons(
                3,
                FunList.Cons(
                    4,
                    FunList.Cons(
                        5,
                        FunList.Nil
                    )
                )
            )
        )
    )

    println("연습문제 5-1 : $list")

    val list2: FunList<Double> = FunList.Cons(
        1.0,
        FunList.Cons(
            2.0,
            FunList.Cons(
                3.0,
                FunList.Cons(
                    4.0,
                    FunList.Cons(
                        5.0,
                        FunList.Nil
                    )
                )
            )
        )
    )
    println("연습문제 5-2 : $list2")
    /* * 연습문제 5-1, 5-2 p.117*/

    // add Head, appendTail

    // 연슴문제 5-3
    val emptyList = FunList.Nil
    runCatching {
        emptyList.getHead()
    }.recover {
        println("연습문제 5-3 : ${it is NoSuchElementException}")
    }

    // p.126 map 함수 예
    val intList = funListOf(1, 2, 3)
    val doubleList = funListOf(1.0, 2.0, 3.0)

    println("int map : ${intList.map{it + 3}.getHead()}")

    // foldLeft함수 사용
    println("(p.129) 코드 5-20 : ${sumByFoldLeft(intList)}")

    // foldRight함수
    println("(p.131) 코드 5-26 : ${intList.foldRight(0){x,acc -> x - acc}}")
}

// funList를 생성하는 유틸 함수
/* p.126 - 127
 *  * vararg는 가변 인자를 받을 때 사용되는 코틀린 예약어 이다. 따라서 elements는 T타입 가진 하나 이상의 값을 입력받을 수 있다.
 *  * toFunList 함수에서는 복잡한 생성 과정을 재귀로 간결하게 작성했다.
 *  * toFunList 함수에서 사용된 this는 가변 인자로 받은 elements가 되는데, 코틀린 내부적으로 Array<T>와 동일하게 취급된다.
 *  * 따라서 배열의 첫 번째 값 this[0]을 head로 나머지 값 this.copyOfRange(1, this.size)를 toFunList로 재귀호출한 결과를 tail로 사용했다.
 */
fun <T> funListOf(vararg elements: T): FunList<T> = elements.toFunList()

private fun <T> Array<out T>.toFunList(): FunList<T> = when {
    this.isEmpty() -> FunList.Nil
    else -> FunList.Cons(this[0], this.copyOfRange(1, this.size).toFunList())
}

// p.129 foldLeft 함수를 사용해서 작성한 sum
fun sumByFoldLeft(list: FunList<Int>): Int = list.foldLeft(0) { acc, x -> acc + x }
