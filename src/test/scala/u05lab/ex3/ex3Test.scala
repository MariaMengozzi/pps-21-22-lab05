package u05lab.ex3

import org.junit.Test
import u05lab.ex3.PerformanceUtils.measure

class ex3Test:

  @Test
  def testLinearSequences(): Unit =
    println("---- Immutable ----")
    val l = List(1, 2, 3)
    measure("List size")(l.size)
    measure("List get")(l(2))
    measure("List rem")(l.take(3))
    measure("List add")(4 :: l)
    println("---- Mutable ----")
    val mL = scala.collection.mutable.ListBuffer(1, 2, 3)
    measure("ListBuffer size")(mL.size)
    measure("ListBuffer get")(mL(2))
    measure("ListBuffer rem")(mL.take(3))
    measure("ListBuffer add")(4 :: l)

  @Test
  def testIndedSequences(): Unit =
    println("---- Immutable ----")
    val v = Vector(1, 2, 3)
    measure("Vector size")(v.size)
    measure("Vector get")(v(2))
    measure("Vector rem")(v.take(3))
    measure("Vector add")(v :+ 4)
    println()
    val a = Array(1, 2, 3)
    measure("Array size")(a.size)
    measure("Array get")(a(2))
    measure("Array rem")(a.take(3))
    measure("Array add")(a :+ 4)
    println("---- Mutable ----")
    val ab = scala.collection.mutable.ArrayBuffer(1, 2, 3)
    measure("ArrayBuffer size")(ab.size)
    measure("ArrayBuffer get")(ab(2))
    measure("ArrayBuffer rem")(ab.take(3))
    measure("ArrayBuffer add")(ab :+ 4)

  @Test
  def testSets(): Unit =
    println("---- Immutable ----")
    val s = Set(1, 2, 3)
    measure("Set size")(s.size)
    measure("Set get")(s(2))
    measure("Set rem")(s.take(3))
    measure("Set add")(s + 4)
    println("---- Mutable ----")
    val ms = scala.collection.mutable.Set(1, 2, 3)
    measure("MSet size")(ms.size)
    measure("MSet get")(ms(2))
    measure("MSet rem")(ms.take(3))
    measure("MSet add")(ms + 4)

  @Test
  def testMaps(): Unit =
    println("---- Immutable ----")
    val m = Map(1 -> "a", 2 -> "b", 3 -> "c" )
    measure("Map size")(m.size)
    measure("Map get")(m.get(2))
    measure("Map rem")(m.take(3))
    measure("Map add")(m + (4 -> "d"))
    println("---- Mutable ----")
    val mm = scala.collection.mutable.Map(1 -> "a", 2 -> "b", 3 -> "c" )
    measure("MMap size")(mm.size)
    measure("MMap get")(mm.get(2))
    measure("MMap rem")(mm.take(3))
    measure("MMap add")(mm + (4 -> "d"))