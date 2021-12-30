// import com.example.kotlinfunctionalpractice.chapter05.FunList
// import com.example.kotlinfunctionalpractice.chapter05.filter
// import com.example.kotlinfunctionalpractice.chapter05.getHead
// import com.example.kotlinfunctionalpractice.chapter05.map
//
// fun main() {
//    val bigIntList = (1..10000000).toFunList()
//    var start = System.currentTimeMillis()
//    funListWay(bigIntList)
//    println("${System.currentTimeMillis() - start} ms")
//
//    val bigIntStream = (1..10000000).toFunStream()
//    start = System.currentTimeMillis()
//    funStreamWay(bigIntStream)
//    println("${System.currentTimeMillis() - start} ms")
// }
//
// fun funListWay(intList: FunList<Int>): Int = intList
//    .map{n -> n*n}
//    .filter {n->n<1000000}
//    .filter{n->n<1000}
//    .map{n->n*10}
//    .getHead()
//
// fun funStreamWay(intList: FunStream<Int>): Int = intList
//    .map{n -> n*n}
//    .filter {n->n<1000000}
//    .filter{n->n<1000}
//    .map{n->n*10}
//    .getHead()
