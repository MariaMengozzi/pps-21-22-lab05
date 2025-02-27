package u05lab.ex1

import u05lab.ex1.List

import scala.annotation.tailrec

// Ex 1. implement the missing methods both with recursion or with using fold, map, flatMap, and filters
// List as a pure interface
enum List[A]:
  case ::(h: A, t: List[A])
  case Nil()
  def ::(h: A): List[A] = List.::(h, this)

  def head: Option[A] = this match
    case h :: t => Some(h)
    case _ => None

  def tail: Option[List[A]] = this match
    case h :: t => Some(t)
    case _ => None

  def append(list: List[A]): List[A] = this match
    case h :: t => h :: t.append(list)
    case _ => list

  def appendWithFold(list: List[A]): List[A] =
    foldRight(list)(_ :: _) //foldRight(list)((a, l) => a :: l)

  def foreach(consumer: A => Unit): Unit = this match
    case h :: t => consumer(h); t.foreach(consumer)
    case _ =>

  def get(pos: Int): Option[A] = this match
    case h :: t if pos == 0 => Some(h)
    case h :: t if pos > 0 => t.get(pos - 1)
    case _ => None

  def filter(predicate: A => Boolean): List[A] = this match
    case h :: t if predicate(h) => h :: t.filter(predicate)
    case _ :: t => t.filter(predicate)
    case _ => Nil()

  def map[B](fun: A => B): List[B] = this match
    case h :: t => fun(h) :: t.map(fun)
    case _ => Nil()

  def flatMap[B](f: A => List[B]): List[B] =
    foldRight[List[B]](Nil())(f(_) append _)

  //consistono in delle iterazioni con accumulatore
  def foldLeft[B](z: B)(op: (B, A) => B): B = this match
    case h :: t => t.foldLeft(op(z, h))(op)
    case Nil() => z

  def foldRight[B](z: B)(f: (A, B) => B): B = this match
    case h :: t => f(h, t.foldRight(z)(f))
    case _ => z

  def length: Int = foldLeft(0)((l, _) => l + 1)

  def isEmpty: Boolean = this match
    case Nil() => true
    case _ => false

  def reverse(): List[A] = foldLeft[List[A]](Nil())((l, e) => e :: l)

  def allMatch(predicate: A => Boolean): Boolean = this match
    case Nil() => true
    case h :: t if predicate(h) => t.allMatch(predicate)
    case _ => false

  /** EXERCISES */
  def zipRightRecursive: List[(A, Int)] =
    def _zipRight(l: List[A], i: Int, out: List[(A, Int)]): List[(A, Int)] = l match
      case h :: t =>  out.append((h, i) :: _zipRight(t, i+1, out))
      case _ => Nil()

    _zipRight(this, 0, Nil())

  def zipRight: List[(A, Int)] =
    this.foldLeft((Nil[(A, Int)](), 0))((acc, elem) => ((elem, acc._2) :: acc._1, acc._2 + 1))._1.reverse()

  def partition(pred: A => Boolean): (List[A], List[A]) =
    this.foldRight((Nil(), Nil()))((e, out) => if pred(e) then (e :: out._1, out._2) else (out._1, e :: out._2))
    //(this.filter(pred(_)), filter(!pred(_)))

  def recoursivePartition(pred: A => Boolean): (List[A], List[A]) =
    def _partition (l: List[A], pred: A => Boolean, out: (List[A], List[A])): (List[A], List[A]) = l match
      case h :: t if pred(h) => _partition(t, pred, (h :: out._1, out._2))
      case h :: t if !pred(h) => _partition(t, pred, (out._1, h:: out._2))
      case _ => (out._1.reverse(), out._2.reverse())

    _partition(this, pred, (Nil(), Nil()))

  def contains(value: A): Boolean = this match
    case h :: t if h == value => true
    case h :: t => t.contains(value)
    case _ => false

  def spanRecoursive(pred: A => Boolean): (List[A], List[A]) =
    def _span(l: List[A], pred: A => Boolean, s: (List[A], List[A])): (List[A], List[A]) = l match
      case h :: t if pred(h) => val next = _span(t, pred, s); (h :: next._1, next._2)
      case _ => (s._1, l)
    _span(this, pred, (Nil(), Nil()))

  def span(pred: A => Boolean): (List[A], List[A]) =
    this.foldLeft(((Nil[A](), Nil[A]()), false))((s,e) =>
      if s._1._1 != Nil() && s._2 && !pred(e) || s._1._2 != Nil()  then
        ( (s._1._1 , s._1._2.append(e :: Nil())), pred(e))
      else ((s._1._1.append(e :: Nil()), s._1._2), pred(e))
    )._1


  /** @throws UnsupportedOperationException if the list is empty */
  def reduce(op: (A, A) => A): A = this match
    case h :: t => t.foldLeft(h)(op)
    case Nil() => throw new UnsupportedOperationException

  def takeRightRecursive(n: Int): List[A] = this.reverse() match
    case h :: t if (n > 0) => t.reverse().takeRightRecursive(n-1).append(h :: Nil())
    case _ => Nil()

  def takeRight(n: Int): List[A] =
    this.zipRight.foldRight(Nil())((e, s) => if e._2 > this.length - 1 - n then e._1 :: s else s)

  def collect[B](pf: PartialFunction[A,B]): List[B] =
    this.filter(pf.isDefinedAt(_)).map(pf)


// Factories
object List:

  def apply[A](elems: A*): List[A] =
    var list: List[A] = Nil()
    for e <- elems.reverse do list = e :: list
    list

  def of[A](elem: A, n: Int): List[A] =
    if n == 0 then Nil() else elem :: of(elem, n - 1)

  def allPositive(l: List[Int]): Boolean = l match
    case Nil() => true
    case h :: t if h > 0 => allPositive(t)
    case _ => false

  def allPositiveWithAllMatch(l: List[Int]): Boolean = l.allMatch(_ > 0)


@main def checkBehaviour(): Unit =
  val reference = List(1, 2, 3, 4)
  println(reference.zipRight) // List((1, 0), (2, 1), (3, 2), (4, 3))
  println(reference.partition(_ % 2 == 0)) // (List(2, 4), List(1, 3))
  println(reference.span(_ % 2 != 0)) // (List(1), List(2, 3, 4))
  println(reference.span(_ < 3)) // (List(1, 2), List(3, 4))
  println(reference.reduce(_ + _)) // 10
  try Nil.reduce[Int](_ + _)
  catch case ex: Exception => println(ex) // prints exception
  println(List(10).reduce(_ + _)) // 10
  //println(reference.takeRight(3)) // List(2, 3, 4)
